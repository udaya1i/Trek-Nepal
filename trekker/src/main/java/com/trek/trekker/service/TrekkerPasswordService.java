package com.trek.trekker.service;

import com.trek.core.constants.OtpPurpose;
import com.trek.core.dto.request.ResetPasswordRequest;
import com.trek.core.entity.OneTimePassword;
import com.trek.core.entity.Trekker;
import com.trek.core.messaging.service.MessagingService;
import com.trek.core.repository.OneTimePasswordRepository;
import com.trek.core.repository.TrekkerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrekkerPasswordService {

    private static final int OTP_LENGTH = 6;
    private static final int OTP_VALID_MINUTES = 10;
    private static final int MAX_ATTEMPTS = 5;

    private final TrekkerRepository trekkerRepository;
    private final OneTimePasswordRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessagingService messagingService;

    @Transactional
    public void forgotPassword(String email) {
        Optional<Trekker> trekkerOpt = trekkerRepository.findByEmail(email);

        if (trekkerOpt.isEmpty()) {
            return;
        }

        Trekker trekker = trekkerOpt.get();

        otpRepository.findFirstByOwnerAndPurposeAndIsActiveTrueOrderByCreatedAtDesc(
                trekker, OtpPurpose.FORGOT_PASSWORD
        ).ifPresent(existing -> {
            existing.setIsActive(false);
            otpRepository.save(existing);
        });

        String rawOtp = generateNumericOtp();

        OneTimePassword otp = new OneTimePassword();
        otp.setOtp(passwordEncoder.encode(rawOtp));
        otp.setAttemptCount(0);
        otp.setIsActive(true);
        otp.setExpiryDate(LocalDateTime.now().plusMinutes(OTP_VALID_MINUTES));
        otp.setPurpose(OtpPurpose.FORGOT_PASSWORD);
        otp.setOwner(trekker);
        otpRepository.save(otp);
        messagingService.sendPasswordResetEmail(trekker.getEmail(), rawOtp);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        Trekker trekker = trekkerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));

        OneTimePassword otp = otpRepository.findFirstByOwnerAndPurposeAndIsActiveTrueOrderByCreatedAtDesc(
                        trekker, OtpPurpose.FORGOT_PASSWORD)
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));

        if (otp.getExpiryDate().isBefore(LocalDateTime.now())) {
            otp.setIsActive(false);
            otpRepository.save(otp);
            throw new IllegalArgumentException("OTP expired");
        }

        if (otp.getAttemptCount() >= MAX_ATTEMPTS) {
            otp.setIsActive(false);
            otpRepository.save(otp);
            throw new IllegalArgumentException("Too many attempts, please request a new OTP");
        }

        if (!passwordEncoder.matches(request.getOtp(), otp.getOtp())) {
            otp.setAttemptCount(otp.getAttemptCount() + 1);
            otpRepository.save(otp);
            throw new IllegalArgumentException("Invalid OTP");
        }

        otp.setIsActive(false);
        otpRepository.save(otp);

        trekker.setOldPassword(trekker.getPassword());
        trekker.setPassword(passwordEncoder.encode(request.getNewPassword()));
        trekker.setPasswordChangedDate(LocalDateTime.now());
        trekker.setPasswordAttemptCount(0L);
        trekker.setAccountLocked(Boolean.FALSE);
        trekkerRepository.save(trekker);

    }

    private String generateNumericOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(TrekkerPasswordService.OTP_LENGTH);
        for (int i = 0; i < TrekkerPasswordService.OTP_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}