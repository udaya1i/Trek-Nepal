package com.trek.admin.facade;

import com.trek.admin.service.SlugBackfillService;
import com.trek.core.dto.response.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SlugMaintenanceFacade {

    private final SlugBackfillService slugBackfillService;

    public ServerResponse<SlugBackfillService.Result> backfill() {
        SlugBackfillService.Result result = slugBackfillService.backfillAll();
        return ServerResponse.<SlugBackfillService.Result>builder()
                .status(HttpStatus.OK)
                .message("Slug backfill completed successfully")
                .data(result)
                .build();
    }
}
