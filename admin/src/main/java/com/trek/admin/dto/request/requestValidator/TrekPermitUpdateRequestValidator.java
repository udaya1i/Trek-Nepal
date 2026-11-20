package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.TrekPermitUpdateCommand;
import com.trek.admin.dto.request.v2.TrekPermitUpdateRequest;
import com.trek.core.admin.service.TrekPermitQueryService;
import com.trek.core.entity.Permit;
import com.trek.core.util.RequestValidator;
import com.trek.core.util.UpdateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekPermitUpdateRequestValidator implements RequestValidator<TrekPermitUpdateRequest, TrekPermitUpdateCommand> {

    private final TrekPermitQueryService trekPermitQueryService;

    @Override
    public TrekPermitUpdateCommand validate(TrekPermitUpdateRequest request) {

        Permit permit = trekPermitQueryService.getById(request.getId());

        TrekPermitUpdateCommand command = new TrekPermitUpdateCommand();
        command.setPermit(permit);
        command.setName(UpdateUtil.getOrDefault(permit.getName(), request.getName()));
        command.setSlug(request.getSlug());
        command.setPermitCode(UpdateUtil.getOrDefault(permit.getPermitCode(), request.getPermitCode()));
        command.setDescription(UpdateUtil.getOrDefault(permit.getDescription(), request.getDescription()));
        command.setIssuingAuthority(UpdateUtil.getOrDefault(permit.getIssuingAuthority(), request.getIssuingAuthority()));
        command.setValidityPeriod(UpdateUtil.getOrDefault(permit.getValidityPeriod(), request.getValidityPeriod()));
        command.setApplicationProcess(UpdateUtil.getOrDefault(permit.getApplicationProcess(), request.getApplicationProcess()));
        command.setRequiredDocuments(UpdateUtil.getOrDefault(permit.getRequiredDocuments(), request.getRequiredDocuments()));
        command.setDoesPermitExpire(UpdateUtil.getOrDefault(permit.getDoesPermitExpire(), request.getDoesPermitExpire()));

        if (request.getPricingRules() != null) {
            command.setPricingRules(request.getPricingRules().stream().map(p -> {
                TrekPermitUpdateCommand.PermitPricingCommand pricingCommand = new TrekPermitUpdateCommand.PermitPricingCommand();
                pricingCommand.setApplicableFor(p.getApplicableFor());
                pricingCommand.setPrice(p.getPrice());
                pricingCommand.setCurrency(p.getCurrency());
                pricingCommand.setValidFrom(p.getValidFrom());
                pricingCommand.setValidTo(p.getValidTo());
                pricingCommand.setSpecialConditions(p.getSpecialConditions());
                return pricingCommand;
            }).toList());
        }

        return command;
    }
}