package com.trek.core.service;

import com.trek.core.dto.response.LocalLevelResponse;
import com.trek.core.entity.District;
import com.trek.core.entity.LocalLevel;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.LocalLevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LocalLevelQueryService {

    private final LocalLevelRepository localLevelRepository;
    private final DistrictQueryService districtQueryService;

    @Transactional(readOnly = true)
    public LocalLevel getById(Long id) {
        return this.localLevelRepository.findById(id).orElseThrow(() -> new RequestValidationException("Invalid district id"));
    }

    @Transactional(readOnly = true)
    public List<LocalLevelResponse> getByDistrict(Long id) {
        District district = districtQueryService.getById(id);
        return this.localLevelRepository.getByDistrict(district)
                .stream()
                .map(localLevel -> new LocalLevelResponse(localLevel.getId(), localLevel.getName())).toList();
    }

    @Transactional(readOnly = true)
    public List<LocalLevelResponse> getAll() {
        return this.localLevelRepository.findAll().stream()
                .map(d -> new LocalLevelResponse(
                        d.getId(),
                        d.getName()))
                .toList();
    }
}
