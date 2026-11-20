package com.trek.admin.controller;

import com.trek.admin.facade.LocalLevelFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.LocalLevelResponse;
import com.trek.core.dto.response.ServerResponse;
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
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.LOCAL_LEVEL)
public class LocalLevelController {

    private final LocalLevelFacade localLevelFacade;

    @GetMapping(AdminPathConstant.ID)
    public ResponseEntity<ServerResponse<List<LocalLevelResponse>>> getByDistrict(@PathVariable Long id) {
        ServerResponse<List<LocalLevelResponse>> serverResponse = localLevelFacade.getByDistrict(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping
    public ResponseEntity<ServerResponse<List<LocalLevelResponse>>> getAll() {
        ServerResponse<List<LocalLevelResponse>> serverResponse = localLevelFacade.getAll();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }
}
