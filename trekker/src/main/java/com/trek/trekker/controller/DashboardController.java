package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.response.DestinationResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.trekker.dto.response.ApplicationStatusResponse;
import com.trek.trekker.dto.response.CategoriesWiseDestinationCountResponse;
import com.trek.trekker.dto.response.PublicDestinationResponse;
import com.trek.trekker.dto.response.StoriesResponse;
import com.trek.trekker.facade.DashboardFacade;
import com.trek.trekker.service.DestinationReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.DASHBOARD)
public class DashboardController {

    private final DashboardFacade dashboardFacade;

    @GetMapping(PublicPathConstant.POPULAR_DESTINATIONS)
    public ResponseEntity<ServerResponse<List<PublicDestinationResponse>>> popularDestinations() {
        ServerResponse<List<PublicDestinationResponse>> serverResponse = dashboardFacade.popularDestinations();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.POPULAR_STORIES)
    public ResponseEntity<ServerResponse<List<StoriesResponse>>> popularStories() {
        ServerResponse<List<StoriesResponse>> serverResponse = dashboardFacade.popularStories();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.APPLICATION_STATUS)
    public ResponseEntity<ServerResponse<ApplicationStatusResponse>> applicationStatus() {
        ServerResponse<ApplicationStatusResponse> serverResponse = dashboardFacade.applicationStatus();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.CATEGORIES)
    public ResponseEntity<ServerResponse<List<CategoriesWiseDestinationCountResponse>>> categoriesWiseCount() {
        ServerResponse<List<CategoriesWiseDestinationCountResponse>> serverResponse = dashboardFacade.categoriesWiseCount();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

}
