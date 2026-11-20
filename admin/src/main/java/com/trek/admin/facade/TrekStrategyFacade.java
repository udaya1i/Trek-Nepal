package com.trek.admin.facade;

import com.trek.admin.dto.command.TrekStrategyAddCommand;
import com.trek.admin.dto.command.TrekStrategyUpdateCommand;
import com.trek.admin.dto.request.requestValidator.TrekStrategyAddRequestValidator;
import com.trek.admin.dto.request.requestValidator.TrekStrategyUpdateRequestValidator;
import com.trek.admin.dto.request.v2.TrekStrategyAddRequest;
import com.trek.admin.dto.request.v2.TrekStrategyUpdateRequest;
import com.trek.admin.dto.response.TrekStrategyAddResponse;
import com.trek.core.dto.response.TrekStrategyDetailResponse;
import com.trek.core.dto.response.TrekStrategyResponse;
import com.trek.admin.service.TrekStrategyService;
import com.trek.core.admin.service.TrekStrategyQueryService;
import com.trek.core.dto.response.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekStrategyFacade {

    private final TrekStrategyQueryService trekStrategyQueryService;
    private final TrekStrategyAddRequestValidator trekStrategyAddRequestValidator;
    private final TrekStrategyUpdateRequestValidator trekStrategyUpdateRequestValidator;
    private final TrekStrategyService trekStrategyService;

    public ServerResponse<TrekStrategyAddResponse> add(TrekStrategyAddRequest request) {
        TrekStrategyAddCommand command = trekStrategyAddRequestValidator.validate(request);
        return ServerResponse.<TrekStrategyAddResponse>builder()
                .message("Trek added successfully")
                .status(HttpStatus.CREATED)
                .data(TrekStrategyAddResponse.from(trekStrategyService.add(command)))
                .build();
    }

    public ServerResponse<TrekStrategyAddResponse> update(TrekStrategyUpdateRequest request) {
        TrekStrategyUpdateCommand command = trekStrategyUpdateRequestValidator.validate(request);
        return ServerResponse.<TrekStrategyAddResponse>builder()
                .message("Trek updated successfully")
                .status(HttpStatus.OK)
                .data(TrekStrategyAddResponse.from(trekStrategyService.update(command)))
                .build();
    }

    public ServerResponse<TrekStrategyDetailResponse> getById(Long id) {
        return ServerResponse.<TrekStrategyDetailResponse>builder()
                .message("Strategy detail fetched successfully")
                .data(TrekStrategyDetailResponse.toResponse(trekStrategyQueryService.getById(id)))
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<TrekStrategyResponse>> getByDestination(Long id) {
        return ServerResponse.<List<TrekStrategyResponse>>builder()
                .message("Strategy(s) fetched successfully")
                .data(TrekStrategyResponse.from(trekStrategyQueryService.getByDestination(id)))
                .status(HttpStatus.OK)
                .build();
    }
}
