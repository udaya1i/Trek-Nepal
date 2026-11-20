package com.trek.core.repository;

import com.trek.core.constants.OtpPurpose;
import com.trek.core.entity.OneTimePassword;
import com.trek.core.entity.Trekker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {

    Optional<OneTimePassword> findFirstByOwnerAndPurposeAndIsActiveTrueOrderByCreatedAtDesc(Trekker owner, OtpPurpose purpose);
}