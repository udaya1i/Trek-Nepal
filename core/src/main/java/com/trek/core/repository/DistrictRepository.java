package com.trek.core.repository;

import com.trek.core.entity.District;
import com.trek.core.entity.Province;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends AbstractRepository<District, Long> {

    public List<District> getByProvince(Province province);
}
