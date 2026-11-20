package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.TrekPackagingUpdateCommand;
import com.trek.admin.dto.request.v2.PackageUpdateRequest;
import com.trek.core.admin.service.TrekPackagingQueryService;
import com.trek.core.entity.TrekPacking;
import com.trek.core.util.RequestValidator;
import com.trek.core.util.UpdateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrekPackagingUpdateRequestValidator implements RequestValidator<PackageUpdateRequest, TrekPackagingUpdateCommand> {

    private final TrekPackagingQueryService trekPackagingQueryService;

    @Override
    public TrekPackagingUpdateCommand validate(PackageUpdateRequest request) {

        TrekPacking trekPacking = trekPackagingQueryService.getById(request.getId());

        TrekPackagingUpdateCommand command = new TrekPackagingUpdateCommand();
        command.setTrekPacking(trekPacking);
        command.setName(UpdateUtil.getOrDefault(trekPacking.getName(), request.getName()));
        command.setItems(UpdateUtil.getOrDefault(trekPacking.getItems(), request.getItems()));

        return command;
    }
}