package com.trek.admin.facade;

import com.trek.core.dto.response.ProvinceResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.entity.Province;
import com.trek.core.service.ProvinceQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProvinceFacade {

    private final ProvinceQueryService provinceQueryService;

    public ServerResponse<Province> getById(Long id) {
        Province province = provinceQueryService.getById(id);
        return ServerResponse.<Province>builder()
                .message("Province fetched successfully")
                .status(HttpStatus.OK)
                .data(province)
                .build();
    }

    public ServerResponse<List<ProvinceResponse>> getAll() {
        return ServerResponse.<List<ProvinceResponse>>builder()
                .message("Provinces fetched successfully")
                .status(HttpStatus.OK)
                .data(provinceQueryService.getAll())
                .build();
    }
}
