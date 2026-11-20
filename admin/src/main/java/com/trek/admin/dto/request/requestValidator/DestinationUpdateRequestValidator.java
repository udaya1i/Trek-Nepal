package com.trek.admin.dto.request.requestValidator;

import com.trek.admin.dto.command.DestinationUpdateCommand;
import com.trek.admin.dto.request.v2.DestinationUpdateRequest;
import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.admin.service.MediaService;
import com.trek.core.entity.Destination;
import com.trek.core.entity.District;
import com.trek.core.entity.LocalLevel;
import com.trek.core.entity.Province;
import com.trek.core.service.DistrictQueryService;
import com.trek.core.service.LocalLevelQueryService;
import com.trek.core.service.ProvinceQueryService;
import com.trek.core.util.RequestValidator;
import com.trek.core.util.UpdateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DestinationUpdateRequestValidator implements RequestValidator<DestinationUpdateRequest, DestinationUpdateCommand> {

    private final DestinationQueryService destinationQueryService;
    private final ProvinceQueryService provinceQueryService;
    private final DistrictQueryService districtQueryService;
    private final LocalLevelQueryService localLevelQueryService;
    private final MediaService mediaService;

    @Override
    public DestinationUpdateCommand validate(DestinationUpdateRequest request) {
        Destination destination = destinationQueryService.getById(request.getId());
        DestinationUpdateCommand command = new DestinationUpdateCommand();
        if (request.getProvince() != null && request.getProvince() > 0){
            Province province = provinceQueryService.getById(request.getProvince());
            command.setProvince(province);
        }
        if (request.getDistrict() != null && request.getDistrict() > 0){
            District district = districtQueryService.getById(request.getDistrict());
            command.setDistrict(district);
        }
        if (request.getLocalLevel() != null && request.getLocalLevel() > 0){
            LocalLevel localLevel = localLevelQueryService.getById(request.getLocalLevel());
            command.setLocalLevel(localLevel);
        }

        command.setDestination(destination);
        command.setDescription(UpdateUtil.getOrDefault(destination.getDescription(), request.getDescription()));
        command.setName(UpdateUtil.getOrDefault(destination.getName(), request.getName()));
        command.setSlug(request.getSlug());
        command.setAltitude(UpdateUtil.getOrDefault(destination.getAltitude(), request.getAltitude()));
        command.setAltitudeNumeric(UpdateUtil.getOrDefault(destination.getAltitudeNumeric(), request.getAltitudeNumeric()));
        command.setLatitude(UpdateUtil.getOrDefault(destination.getLatitude(), request.getLatitude()));
        command.setLatitudeNumeric(UpdateUtil.getOrDefault(destination.getLatitudeNumeric(), request.getLatitudeNumeric()));
        command.setLongitude(UpdateUtil.getOrDefault(destination.getLongitude(), request.getLongitude()));
        command.setLongitudeNumeric(UpdateUtil.getOrDefault(destination.getLongitudeNumeric(), request.getLongitudeNumeric()));
        command.setRegion(UpdateUtil.getOrDefault(destination.getRegion(), request.getRegion()));
        command.setPrimaryAccessCity(UpdateUtil.getOrDefault(destination.getPrimaryAccessCity(), request.getPrimaryAccessCity()));
        command.setDestinationType(UpdateUtil.getOrDefault(destination.getDestinationType(), request.getDestinationType()));
        command.setTrailCategories(UpdateUtil.getOrDefault(destination.getTrailCategory(), request.getTrailCategories()));
        command.setHighlights(UpdateUtil.getOrDefault(destination.getHighlights(), request.getHighlights()));

        command.setImages(mediaService.finaAllByUrl(request.getImages()));
        return command;
    }
}
