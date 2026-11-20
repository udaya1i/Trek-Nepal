package com.trek.core.trekker.service;

import com.trek.core.constants.TrekkerStatus;
import com.trek.core.entity.Trekker;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.TrekkerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekkerQueryService {

    public final TrekkerRepository trekkerRepository;

    public Trekker getById(Long trekkerId) {
        return trekkerRepository.findById(trekkerId)
                .orElseThrow(() -> new RequestValidationException("Trekker not found"));
    }

    public boolean existsByUsername(String username) {
        return trekkerRepository.existsByUsernameAndStatus(username, TrekkerStatus.ACTIVE);
    }

    public boolean existByEmail(String email) {
        return trekkerRepository.existsByEmailAndStatus(email, TrekkerStatus.ACTIVE);
    }


}
