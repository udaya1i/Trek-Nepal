package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.DestinationAddCommand;
import com.trek.admin.dto.request.v2.DestinationAddRequest;
import com.trek.core.admin.service.MediaService;
import com.trek.core.entity.District;
import com.trek.core.entity.LocalLevel;
import com.trek.core.entity.Province;
import com.trek.core.service.DistrictQueryService;
import com.trek.core.service.LocalLevelQueryService;
import com.trek.core.service.ProvinceQueryService;
import com.trek.core.util.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DestinationAddRequestValidator implements RequestValidator<DestinationAddRequest, DestinationAddCommand> {

    private final ProvinceQueryService provinceQueryService;
    private final LocalLevelQueryService localLevelQueryService;
    private final DistrictQueryService districtQueryService;
    private final MediaService mediaService;

    @Override
    public DestinationAddCommand validate(DestinationAddRequest request) {

        Province province = provinceQueryService.getById(request.getProvince());
        LocalLevel localLevel = localLevelQueryService.getById(request.getLocalLevel());
        District district = districtQueryService.getById(request.getDistrict());

        DestinationAddCommand command = new DestinationAddCommand();

        command.setImages(mediaService.finaAllByUrl(request.getImages()));
        command.setRegion(request.getRegion());
        command.setName(request.getName());
        command.setDestinationType(request.getDestinationType());
        command.setAltitude(request.getAltitude());
        command.setAltitudeNumeric(request.getAltitudeNumeric());
        command.setLatitude(request.getLatitude());
        command.setLatitudeNumeric(request.getLatitudeNumeric());
        command.setLongitude(request.getLongitude());
        command.setLongitudeNumeric(request.getLongitudeNumeric());
        command.setDescription(request.getDescription());
        command.setHighlights(request.getHighlights());
        command.setProvince(province);
        command.setTrailCategories(request.getTrailCategories());
        command.setDistrict(district);
        command.setLocalLevel(localLevel);
        command.setPrimaryAccessCity(request.getPrimaryAccessCity());
        command.setDistanceFromAccessCity(request.getDistanceFromAccessCity());

        return command;
    }
}
