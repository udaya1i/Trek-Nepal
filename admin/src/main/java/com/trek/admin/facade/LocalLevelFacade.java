package com.trek.admin.facade;

import com.trek.core.dto.response.LocalLevelResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.service.LocalLevelQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocalLevelFacade {

    private final LocalLevelQueryService localLevelQueryService;

    public ServerResponse<List<LocalLevelResponse>> getByDistrict(Long id) {
        return ServerResponse.<List<LocalLevelResponse>>builder()
                .message("Local level(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(localLevelQueryService.getByDistrict(id))
                .build();
    }

    public ServerResponse<List<LocalLevelResponse>> getAll() {
        return ServerResponse.<List<LocalLevelResponse>>builder()
                .message("Local level(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(localLevelQueryService.getAll())
                .build();
    }
}
