package com.trek.admin.facade;

import com.trek.core.dto.response.DistrictResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.service.DistrictQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DistrictFacade {

    private final DistrictQueryService districtQueryService;

    public ServerResponse<List<DistrictResponse>> getByProvince(Long id) {
        return ServerResponse.<List<DistrictResponse>>builder()
                .message("District(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(districtQueryService.getByProvince(id))
                .build();
    }

    public ServerResponse<List<DistrictResponse>> getAll() {
        return ServerResponse.<List<DistrictResponse>>builder()
                .message("District(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(districtQueryService.getAll())
                .build();
    }
}
