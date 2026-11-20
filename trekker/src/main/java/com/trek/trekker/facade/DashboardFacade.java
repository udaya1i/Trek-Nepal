package com.trek.trekker.facade;

import com.trek.core.dto.response.DestinationResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.trekker.dto.response.ApplicationStatusResponse;
import com.trek.trekker.dto.response.CategoriesWiseDestinationCountResponse;
import com.trek.trekker.dto.response.PublicDestinationResponse;
import com.trek.trekker.dto.response.StoriesResponse;
import com.trek.trekker.service.DashboardService;
import com.trek.trekker.service.DestinationReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DashboardFacade {

    private final DashboardService dashboardService;

    public ServerResponse<List<PublicDestinationResponse>> popularDestinations() {
        return ServerResponse.<List<PublicDestinationResponse>>builder()
                .status(HttpStatus.OK)
                .data(dashboardService.popularDestinations())
                .message("Featured treks fetched")
                .build();
    }

    public ServerResponse<List<StoriesResponse>> popularStories() {
        return ServerResponse.<List<StoriesResponse>>builder()
                .status(HttpStatus.OK)
                .data(dashboardService.popularStories())
                .message("Featured treks fetched")
                .build();
    }

    public ServerResponse<ApplicationStatusResponse> applicationStatus() {
        return ServerResponse.<ApplicationStatusResponse>builder()
                .status(HttpStatus.OK)
                .data(dashboardService.getPlatformStats())
                .message("Featured treks fetched")
                .build();
    }

        public ServerResponse<List<CategoriesWiseDestinationCountResponse>> categoriesWiseCount() {
        List<CategoriesWiseDestinationCountResponse> responses = dashboardService.getTrailCategory().stream().map(
                (category) -> {
                    return new CategoriesWiseDestinationCountResponse(category.getCategoryName(), category.getTotalNumber());
                }
        ).toList();
        return ServerResponse.<List<CategoriesWiseDestinationCountResponse>>builder()
                .status(HttpStatus.OK)
                .data(responses)
                .message("Featured treks fetched")
                .build();
    }

}
