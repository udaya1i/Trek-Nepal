package com.trek.admin.service;

import com.trek.admin.dto.command.TrekPermitAddCommand;
import com.trek.admin.dto.command.TrekPermitUpdateCommand;
import com.trek.core.admin.service.SlugAssignmentService;
import com.trek.core.constants.SluggableType;
import com.trek.core.entity.Permit;
import com.trek.core.entity.PermitPricing;
import com.trek.core.repository.TrekPermitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekPermitService {

    private final TrekPermitRepository trekPermitRepository;
    private final SlugAssignmentService slugAssignmentService;

    public Permit add(TrekPermitAddCommand command) {

        Permit permit = new Permit();
        permit.setName(command.getName());
        permit.setPermitCode(command.getPermitCode());
        permit.setDescription(command.getDescription());
        permit.setIssuingAuthority(command.getIssuingAuthority());
        permit.setValidityPeriod(command.getValidityPeriod());
        permit.setApplicationProcess(command.getApplicationProcess());
        permit.setRequiredDocuments(command.getRequiredDocuments());
        permit.setDoesPermitExpire(command.getDoesPermitExpire());
        permit.setSlug(slugAssignmentService.ensureUniqueSlug(command.getName(), trekPermitRepository::existsBySlug));

        if (command.getPricingRules() != null) {
            List<PermitPricing> pricingRules = command.getPricingRules().stream()
                    .map(pricingCommand -> {
                        PermitPricing pricing = new PermitPricing();
                        pricing.setPermit(permit);
                        pricing.setApplicableFor(pricingCommand.getApplicableFor());
                        pricing.setPrice(pricingCommand.getPrice());
                        pricing.setCurrency(pricingCommand.getCurrency());
                        pricing.setValidFrom(pricingCommand.getValidFrom());
                        pricing.setValidTo(pricingCommand.getValidTo());
                        pricing.setSpecialConditions(pricingCommand.getSpecialConditions());
                        return pricing;
                    })
                    .toList();
            permit.setPricingRules(pricingRules);
        }
        return trekPermitRepository.save(permit);
    }

    @Transactional
    public Permit update(TrekPermitUpdateCommand command) {

        Permit permit = command.getPermit();
        permit.setName(command.getName());
        permit.setPermitCode(command.getPermitCode());
        permit.setDescription(command.getDescription());
        permit.setIssuingAuthority(command.getIssuingAuthority());
        permit.setValidityPeriod(command.getValidityPeriod());
        permit.setApplicationProcess(command.getApplicationProcess());
        permit.setRequiredDocuments(command.getRequiredDocuments());
        permit.setDoesPermitExpire(command.getDoesPermitExpire());
        permit.setSlug(slugAssignmentService.resolveSlugOnUpdate(SluggableType.PERMIT, permit.getId(),
                permit.getSlug(), command.getSlug(), permit.getName(), trekPermitRepository::existsBySlug));

        if (command.getPricingRules() != null) {
            List<PermitPricing> pricingRules = command.getPricingRules().stream()
                    .map(pricingCommand -> {
                        PermitPricing pricing = new PermitPricing();
                        pricing.setPermit(permit);
                        pricing.setApplicableFor(pricingCommand.getApplicableFor());
                        pricing.setPrice(pricingCommand.getPrice());
                        pricing.setCurrency(pricingCommand.getCurrency());
                        pricing.setValidFrom(pricingCommand.getValidFrom());
                        pricing.setValidTo(pricingCommand.getValidTo());
                        pricing.setSpecialConditions(pricingCommand.getSpecialConditions());
                        return pricing;
                    })
                    .toList();
            permit.setPricingRules(pricingRules);
        }
        return trekPermitRepository.save(permit);
    }

}
