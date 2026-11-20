package com.trek.trekker.dto.response;

import com.trek.core.entity.Destination;
import lombok.Data;

import java.util.List;

@Data
public class DestinationForStoryResponse {
    private Long id;
    private String region;
    private String name;
    private String destinationType;

    public static List<DestinationForStoryResponse> from(List<Destination> destinations) {
        return destinations.stream().map(DestinationForStoryResponse::from).toList();
    }

    public static DestinationForStoryResponse from(Destination destinations) {
        DestinationForStoryResponse response = new DestinationForStoryResponse();
        response.setId(destinations.getId());
        response.setRegion(destinations.getRegion());
        response.setName(destinations.getName());
        response.setDestinationType(destinations.getDestinationType().name());
        return response;
    }
}
