package com.trek.trekker.service;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class RecaptchaService {

    public ResponseEntity<String> verify(String token) {
        String secretKey = "SecretKey";
        String url = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.ok("reCAPTCHA token is valid");
        } else {
            return ResponseEntity.badRequest().body("reCAPTCHA token is invalid");
        }
    }
}
