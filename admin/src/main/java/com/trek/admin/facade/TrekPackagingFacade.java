package com.trek.admin.facade;

import com.trek.admin.dto.command.TrekPackagingAddCommand;
import com.trek.admin.dto.command.TrekPackagingUpdateCommand;
import com.trek.admin.dto.request.TrekPackagingAddRequest;
import com.trek.admin.dto.request.requestValidator.TrekPackagingRequestValidator;
import com.trek.admin.dto.request.requestValidator.TrekPackagingUpdateRequestValidator;
import com.trek.admin.dto.request.v2.PackageUpdateRequest;
import com.trek.admin.service.TrekPackagingService;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.TrekPackagingResponse;
import com.trek.core.entity.TrekPacking;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekPackagingFacade {

    private final TrekPackagingService trekPackagingService;
    private final TrekPackagingRequestValidator requestValidator;
    private final TrekPackagingUpdateRequestValidator updateRequestValidator;

    public ServerResponse<Void> add(TrekPackagingAddRequest request) {
        TrekPackagingAddCommand validate = requestValidator.validate(request);
        trekPackagingService.add(validate);
        return ServerResponse.<Void>builder()
                .message("Trek packaging added successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    public ServerResponse<Void> update(PackageUpdateRequest request) {
        TrekPackagingUpdateCommand command = updateRequestValidator.validate(request);
        trekPackagingService.update(command);
        return ServerResponse.<Void>builder()
                .message("Trek packaging updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<TrekPackagingResponse>> getByTrek(Long id) {
        List<TrekPacking> treks = trekPackagingService.getByTrekStrategy(id);

        return ServerResponse.<List<TrekPackagingResponse>>builder()
                .message("Trek Packaging fetched successfully")
                .data(treks.stream().map(items ->
                        new TrekPackagingResponse(
                                items.getName(),
                                items.getItems())).toList())
                .status(HttpStatus.OK)
                .build();
    }
}
