package com.trek.admin.facade;

import com.trek.admin.dto.command.TrekPermitAddCommand;
import com.trek.admin.dto.command.TrekPermitUpdateCommand;
import com.trek.admin.dto.request.TrekPermitAddRequest;
import com.trek.admin.dto.request.requestValidator.TrekPermitAddRequestValidator;
import com.trek.admin.dto.request.requestValidator.TrekPermitUpdateRequestValidator;
import com.trek.admin.dto.request.v2.TrekPermitUpdateRequest;
import com.trek.core.dto.response.DestinationPermitResponse;
import com.trek.admin.service.TrekPermitService;
import com.trek.core.entity.Permit;
import com.trek.core.admin.service.TrekPermitQueryService;
import com.trek.core.dto.response.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekPermitFacade {

    private final TrekPermitService trekPermitService;
    private final TrekPermitQueryService trekPermitQueryService;
    private final TrekPermitAddRequestValidator requestValidator;
    private final TrekPermitUpdateRequestValidator updateRequestValidator;

    public ServerResponse<Void> add(TrekPermitAddRequest request) {
        TrekPermitAddCommand validate = requestValidator.validate(request);
        trekPermitService.add(validate);
        return ServerResponse.<Void>builder()
                .message("Trek Permit saved successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    public ServerResponse<Void> update(TrekPermitUpdateRequest request) {
        TrekPermitUpdateCommand command = updateRequestValidator.validate(request);
        trekPermitService.update(command);
        return ServerResponse.<Void>builder()
                .message("Trek Permit updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<DestinationPermitResponse>> getByTrek(Long trekId) {
        List<Permit> permits = trekPermitQueryService.getAllByStrategy(trekId);
        return ServerResponse.<List<DestinationPermitResponse>>builder()
                .message("Permit(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(DestinationPermitResponse.of(permits))
                .build();
    }
}
