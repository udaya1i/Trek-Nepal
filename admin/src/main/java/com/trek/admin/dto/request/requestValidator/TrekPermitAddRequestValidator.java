package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.TrekPermitAddCommand;
import com.trek.admin.dto.request.TrekPermitAddRequest;
import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.entity.Destination;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.admin.service.TrekStrategyQueryService;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekPermitAddRequestValidator implements RequestValidator<TrekPermitAddRequest, TrekPermitAddCommand> {

    private final DestinationQueryService destinationQueryService;

    @Override
    public TrekPermitAddCommand validate(TrekPermitAddRequest request) {

        Destination destination = destinationQueryService.getById(request.getDestination());

        TrekPermitAddCommand command = new TrekPermitAddCommand();
        command.setDestination(destination);
        command.setName(request.getName());
        command.setPermitCode(request.getPermitCode());
        command.setDescription(request.getDescription());
        command.setIssuingAuthority(request.getIssuingAuthority());
        command.setValidityPeriod(request.getValidityPeriod());
        command.setApplicationProcess(request.getApplicationProcess());
        command.setRequiredDocuments(request.getRequiredDocuments());
        command.setDoesPermitExpire(request.getDoesPermitExpire());
        command.setPricingRules(request.getPricingRules()
                .stream().map(p -> {
                    TrekPermitAddCommand.PermitPricingCommand pricingCommand = new TrekPermitAddCommand.PermitPricingCommand();
                    pricingCommand.setApplicableFor(p.getApplicableFor());
                    pricingCommand.setPrice(p.getPrice());
                    pricingCommand.setCurrency(p.getCurrency());
                    pricingCommand.setValidFrom(p.getValidFrom());
                    pricingCommand.setValidTo(p.getValidTo());
                    pricingCommand.setSpecialConditions(p.getSpecialConditions());
                    return pricingCommand;
                }).toList());
        return command;

    }

}
