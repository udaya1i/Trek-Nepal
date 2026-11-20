package com.trek.admin.controller;

import com.trek.admin.facade.SlugMaintenanceFacade;
import com.trek.admin.service.SlugBackfillService;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * One-time maintenance endpoint to backfill slugs for rows created before the
 * slug column existed. Safe to call repeatedly - only touches null slugs.
 */
@RestController
@AllArgsConstructor
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.SLUG)
public class SlugMaintenanceController {

    private final SlugMaintenanceFacade slugMaintenanceFacade;

    @PostMapping(AdminPathConstant.BACKFILL)
    public ResponseEntity<ServerResponse<SlugBackfillService.Result>> backfill() {
        ServerResponse<SlugBackfillService.Result> serverResponse = slugMaintenanceFacade.backfill();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }
}
