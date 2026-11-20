package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.response.DistrictResponse;
import com.trek.core.dto.response.LocalLevelResponse;
import com.trek.core.dto.response.ProvinceResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.trekker.facade.LocationFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.LOCATION)
public class LocationController {

    private final LocationFacade locationFacade;

    @GetMapping(PublicPathConstant.PROVINCES)
    public ResponseEntity<ServerResponse<List<ProvinceResponse>>> getAllProvince() {
        ServerResponse<List<ProvinceResponse>> serverResponse = locationFacade.getAllProvinces();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.PROVINCES + PublicPathConstant.DISTRICTS + PublicPathConstant.ID)
    public ResponseEntity<ServerResponse<List<DistrictResponse>>> getAllDistrictByProvince(@PathVariable Long id) {
        ServerResponse<List<DistrictResponse>> serverResponse = locationFacade.getDistrictsByProvince(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.DISTRICTS)
    public ResponseEntity<ServerResponse<List<DistrictResponse>>> getAllDistricts() {
        ServerResponse<List<DistrictResponse>> serverResponse = locationFacade.getAllDistricts();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.LOCAL_LEVELS + PublicPathConstant.DISTRICTS + PublicPathConstant.ID)
    public ResponseEntity<ServerResponse<List<LocalLevelResponse>>> getAllLocalLevelByDistrict(@PathVariable Long id) {
        ServerResponse<List<LocalLevelResponse>> serverResponse = locationFacade.getLocalLevelsByDistrict(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.LOCAL_LEVELS)
    public ResponseEntity<ServerResponse<List<LocalLevelResponse>>> getAllLocalLevel() {
        ServerResponse<List<LocalLevelResponse>> serverResponse = locationFacade.getAllLocalLevels();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }
}
