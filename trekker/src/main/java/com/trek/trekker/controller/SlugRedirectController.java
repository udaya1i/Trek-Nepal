package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.constants.SluggableType;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.dto.response.SlugRedirectResponse;
import com.trek.trekker.facade.SlugRedirectFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.SLUG_REDIRECT)
@AllArgsConstructor
public class SlugRedirectController {

    private final SlugRedirectFacade slugRedirectFacade;

    @GetMapping
    public ResponseEntity<ServerResponse<SlugRedirectResponse>> resolve(@RequestParam SluggableType type, @RequestParam String slug) {
        ServerResponse<SlugRedirectResponse> serverResponse = slugRedirectFacade.resolve(type, slug);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }
}
