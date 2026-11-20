package com.trek.trekker.service;

import com.trek.core.constants.TrekkerStatus;
import com.trek.core.entity.Trekker;
import com.trek.core.messaging.service.MessagingService;
import com.trek.core.repository.RoleRepository;
import com.trek.core.repository.TrekkerRepository;
import com.trek.trekker.dto.request.TrekkerOauthResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekkerOauthService {

    private final TrekkerRepository trekkerRepository;
    private final RoleRepository roleRepository;
    private final MessagingService messagingService;

    public TrekkerOauthResult findOrCreateFromOauth(String googleId, String email, String name) {
        return trekkerRepository.findByGoogleId(googleId)
                .map(existing -> new TrekkerOauthResult(existing, false))
                .or(() -> trekkerRepository.findByEmail(email).map(existing -> {
                    if (existing.getGoogleId() == null) {
                        existing.setGoogleId(googleId);
                        existing.setProvider("GOOGLE");
                        trekkerRepository.save(existing);
                    }
                    return new TrekkerOauthResult(existing, false);
                }))
                .orElseGet(() -> new TrekkerOauthResult(createNewGoogleTrekker(googleId, email, name), true));
    }

    private Trekker createNewGoogleTrekker(String googleId, String email, String name) {
        Trekker trekker = new Trekker();
        trekker.setGoogleId(googleId);
        trekker.setFullName(name);
        trekker.setEmail(email);
        trekker.setUsername(generateUniqueUsername(email));
        trekker.setProvider("GOOGLE");
        trekker.setPassword(null);
        trekker.setAccountLocked(false);
        trekker.setPasswordAttemptCount(0L);
        trekker.setIsVerified("true");

        trekker.setRole(roleRepository.findByName("TREKKER")
                .orElseThrow(() -> new IllegalStateException("Default role not configured")));

        trekker.setStatus(TrekkerStatus.ACTIVE);
        Trekker savedTrekker = trekkerRepository.save(trekker);
        messagingService.sendWelcomeEmail(savedTrekker);
        return savedTrekker;
    }

    private String generateUniqueUsername(String email) {
        String base = email.split("@")[0].replaceAll("[^a-zA-Z0-9_]", "");
        String candidate = base;
        int suffix = 1;
        while (trekkerRepository.findByUsername(candidate).isPresent()) {
            candidate = base + suffix++;
        }
        return candidate;
    }
}