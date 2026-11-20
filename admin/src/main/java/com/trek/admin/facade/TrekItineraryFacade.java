package com.trek.admin.facade;

import com.trek.admin.dto.command.TrekItineraryCommand;
import com.trek.admin.dto.command.TrekItineraryUpdateCommand;
import com.trek.admin.dto.request.TrekItineraryRequest;
import com.trek.admin.dto.request.requestValidator.TrekItineraryRequestValidator;
import com.trek.admin.dto.request.requestValidator.TrekItineraryUpdateRequestValidator;
import com.trek.admin.dto.request.v2.TrekItineraryUpdateRequest;
import com.trek.core.dto.response.TrekItineraryDetailResponse;
import com.trek.admin.service.TrekItineraryService;
import com.trek.core.dto.response.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekItineraryFacade {

    private final TrekItineraryService trekItineraryService;
    private final TrekItineraryRequestValidator trekItineraryRequestValidator;
    private final TrekItineraryUpdateRequestValidator trekItineraryUpdateRequestValidator;

    public ServerResponse<TrekItineraryDetailResponse> addItinerary(TrekItineraryRequest request) {
        TrekItineraryCommand command = trekItineraryRequestValidator.validate(request);
        TrekItineraryDetailResponse trekItineraryDetailResponse = trekItineraryService.addItinerary(command);
        return ServerResponse.<TrekItineraryDetailResponse>builder()
                .data(trekItineraryDetailResponse)
                .message("Itinerary added successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    public ServerResponse<TrekItineraryDetailResponse> updateItinerary(TrekItineraryUpdateRequest request) {
        TrekItineraryUpdateCommand command = trekItineraryUpdateRequestValidator.validate(request);
        TrekItineraryDetailResponse trekItineraryDetailResponse = trekItineraryService.updateItinerary(command);
        return ServerResponse.<TrekItineraryDetailResponse>builder()
                .data(trekItineraryDetailResponse)
                .message("Itinerary updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    public ServerResponse<List<TrekItineraryDetailResponse>> getAll(Long strategyId) {
        List<TrekItineraryDetailResponse> itinerary = trekItineraryService.getAllByStrategy(strategyId);
        return ServerResponse.<List<TrekItineraryDetailResponse>>builder()
                .message("Itinerary(s) fetched successfully")
                .status(HttpStatus.OK)
                .data(itinerary)
                .build();
    }

    public ServerResponse<TrekItineraryDetailResponse> getByTrek(Long trekId) {
        return ServerResponse.<TrekItineraryDetailResponse>builder()
                .message("Itinerary detail fetched successfully")
                .status(HttpStatus.OK)
                .data(TrekItineraryDetailResponse.of(trekItineraryService.getById(trekId)))
                .build();
    }
}
