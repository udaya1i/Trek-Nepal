package com.trek.trekker.dto.request.requestValidator;

import com.trek.core.constants.TrekkerStatus;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.TrekkerRepository;
import com.trek.core.util.RequestValidator;
import com.trek.trekker.dto.command.TrekkerCreateCommand;
import com.trek.trekker.dto.request.TrekkerCreateRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekkerCreateRequestValidator implements RequestValidator<TrekkerCreateRequest, TrekkerCreateCommand> {

    private final PasswordEncoder passwordEncoder;
    private final TrekkerRepository trekkerRepository;

    @Override
    public TrekkerCreateCommand validate(TrekkerCreateRequest request) {
        if (trekkerRepository.existsByUsernameAndStatus(request.getUsername(), TrekkerStatus.ACTIVE)) {
            throw new RequestValidationException("Username already used");
        }
        if (trekkerRepository.existsByEmailAndStatus(request.getEmail(), TrekkerStatus.ACTIVE)) {
            throw new RequestValidationException("Email address already used");
        }
        TrekkerCreateCommand command = new TrekkerCreateCommand();
        command.setAvatar(request.getAvatar());
        command.setBio(request.getBio());
        command.setEmail(request.getEmail());
        command.setLocation(request.getLocation());
        command.setUsername(request.getUsername());
        command.setPassword(passwordEncoder.encode(request.getPassword()));
        command.setOldPassword(request.getPassword());
        command.setFullName(request.getFullName());
        command.setCoverImage(request.getCoverImage());
        return command;
    }
}
