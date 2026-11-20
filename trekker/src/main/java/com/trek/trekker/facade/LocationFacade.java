package com.trek.trekker.facade;

import com.trek.core.dto.response.DistrictResponse;
import com.trek.core.dto.response.LocalLevelResponse;
import com.trek.core.dto.response.ProvinceResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.service.DistrictQueryService;
import com.trek.core.service.LocalLevelQueryService;
import com.trek.core.service.ProvinceQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationFacade {

    private final DistrictQueryService districtQueryService;
    private final LocalLevelQueryService localLevelQueryService;
    private final ProvinceQueryService provinceQueryService;

    public ServerResponse<List<ProvinceResponse>> getAllProvinces() {
        return ServerResponse.<List<ProvinceResponse>>builder()
                .message("Provinces fetched successfully")
                .status(HttpStatus.OK)
                .data(provinceQueryService.getAll())
                .build();
    }

    public ServerResponse<List<DistrictResponse>> getDistrictsByProvince(Long id) {
        return ServerResponse.<List<DistrictResponse>>builder()
                .message("District(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(districtQueryService.getByProvince(id))
                .build();
    }

    public ServerResponse<List<DistrictResponse>> getAllDistricts() {
        return ServerResponse.<List<DistrictResponse>>builder()
                .message("District(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(districtQueryService.getAll())
                .build();
    }

    public ServerResponse<List<LocalLevelResponse>> getLocalLevelsByDistrict(Long id) {
        return ServerResponse.<List<LocalLevelResponse>>builder()
                .message("Local level(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(localLevelQueryService.getByDistrict(id))
                .build();
    }

    public ServerResponse<List<LocalLevelResponse>> getAllLocalLevels() {
        return ServerResponse.<List<LocalLevelResponse>>builder()
                .message("Local level(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(localLevelQueryService.getAll())
                .build();
    }
}
