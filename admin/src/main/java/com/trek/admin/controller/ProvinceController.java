package com.trek.admin.controller;

import com.trek.admin.facade.ProvinceFacade;
import com.trek.core.constants.AdminPathConstant;
import com.trek.core.dto.response.ProvinceResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.entity.Province;
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
@RequestMapping(AdminPathConstant.BASE_URL + AdminPathConstant.PROVINCE)
public class ProvinceController {

    private final ProvinceFacade provinceFacade;

    @GetMapping(AdminPathConstant.ID)
    public ResponseEntity<ServerResponse<Province>> getById(@PathVariable Long id) {
        ServerResponse<Province> serverResponse = provinceFacade.getById(id);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @GetMapping
    public ResponseEntity<ServerResponse<List<ProvinceResponse>>> getAll() {
        ServerResponse<List<ProvinceResponse>> serverResponse = provinceFacade.getAll();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }
}
