package com.trek.core.service;


import com.trek.core.dto.response.DistrictResponse;
import com.trek.core.entity.District;
import com.trek.core.entity.Province;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.DistrictRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DistrictQueryService {

    public final ProvinceQueryService provinceQueryService;
    private final DistrictRepository districtRepository;

    public District getById(Long id) {
        return this.districtRepository.findById(id).orElseThrow(() -> new RequestValidationException("Invalid district id"));
     }

    public List<DistrictResponse> getByProvince(Long id) {
        Province province = provinceQueryService.getById(id);
        return this.districtRepository.getByProvince(province).stream()
                .map(district -> {
                    return new DistrictResponse(
                            district.getId(),
                            district.getName(),
                            district.getProvince().getId()
                    );
                }).toList();
    }

    public List<DistrictResponse> getAll() {
        return this.districtRepository.findAll().stream()
                .map(d -> new DistrictResponse(
                        d.getId(),
                        d.getName(),
                        d.getProvince().getId()))
                .toList();
    }
}
