package com.trek.trekker.facade;

import com.trek.core.constants.SluggableType;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.SlugRedirectResponse;
import com.trek.core.trekker.service.SlugRedirectQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SlugRedirectFacade {

    private final SlugRedirectQueryService slugRedirectQueryService;

    public ServerResponse<SlugRedirectResponse> resolve(SluggableType type, String slug) {
        return slugRedirectQueryService.resolveCurrentSlug(type, slug)
                .map(currentSlug -> ServerResponse.<SlugRedirectResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Redirect resolved successfully")
                        .data(new SlugRedirectResponse(currentSlug))
                        .build())
                .orElseGet(() -> ServerResponse.<SlugRedirectResponse>builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message("No redirect found for this slug")
                        .build());
    }
}
