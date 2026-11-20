package com.trek.core.service;

import com.trek.core.dto.response.ProvinceResponse;
import com.trek.core.entity.Province;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.ProvinceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProvinceQueryService {

    private final ProvinceRepository provinceRepository;

    public Province getById(Long id) {
        return this.provinceRepository.findById(id).orElseThrow(() -> new RequestValidationException("Invalid province id"));
    }

    public List<ProvinceResponse> getAll() {
        return this.provinceRepository.findAll().stream()
                .map(d -> new ProvinceResponse(
                        d.getId(),
                        d.getName()))
                .toList();
    }
}
