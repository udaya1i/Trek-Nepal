package com.trek.trekker.dto.request.requestValidator;

import com.trek.core.constants.TrekkerStatus;
import com.trek.core.entity.Trekker;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.TrekkerRepository;
import com.trek.core.util.RequestValidator;
import com.trek.core.util.UpdateUtil;
import com.trek.trekker.dto.command.TrekkerEditCommand;
import com.trek.trekker.dto.request.TrekkerEditRequest;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekkerEditRequestValidator implements RequestValidator<TrekkerEditRequest, TrekkerEditCommand> {

    private final TrekkerSecurityContextHolderHolder securityContextHolderHolder;
    private final TrekkerRepository trekkerRepository;

    @Override
    public TrekkerEditCommand validate(TrekkerEditRequest request) {
        Trekker trekker = securityContextHolderHolder.getCurrentUser();

        if (request.getUsername() != null
                && !request.getUsername().equals(trekker.getUsername())
                && trekkerRepository.existsByUsernameAndStatus(request.getUsername(), TrekkerStatus.ACTIVE)) {
            throw new RequestValidationException("Username already used");
        }

        TrekkerEditCommand command = new TrekkerEditCommand();
        command.setTrekker(trekker);
        command.setFullName(UpdateUtil.getOrDefault(trekker.getFullName(), request.getFullName()));
        command.setUsername(UpdateUtil.getOrDefault(trekker.getUsername(), request.getUsername()));
        command.setBio(UpdateUtil.getOrDefault(trekker.getBio(), request.getBio()));
        command.setLocation(UpdateUtil.getOrDefault(trekker.getLocation(), request.getLocation()));
        return command;
    }
}
