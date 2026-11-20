package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.TrekPackagingAddCommand;
import com.trek.admin.dto.request.TrekPackagingAddRequest;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.admin.service.TrekStrategyQueryService;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekPackagingRequestValidator implements RequestValidator<TrekPackagingAddRequest, TrekPackagingAddCommand> {

    private final TrekStrategyQueryService trekStrategyQueryService;

    @Override
    public TrekPackagingAddCommand validate(TrekPackagingAddRequest request) {
        TrekStrategy strategy = trekStrategyQueryService.getById(request.getStrategyId());
        TrekPackagingAddCommand command = new TrekPackagingAddCommand();
        command.setTrekStrategy(strategy);
        command.setName(request.getName());
        command.setItems(request.getItems());
        return command;
    }
}
