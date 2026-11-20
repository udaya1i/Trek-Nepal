package com.trek.core.datainit;

import com.trek.core.datainit.yaml.LocationYaml;
import com.trek.core.entity.District;
import com.trek.core.entity.LocalLevel;
import com.trek.core.entity.Province;
import com.trek.core.repository.DistrictRepository;
import com.trek.core.repository.LocalLevelRepository;
import com.trek.core.repository.ProvinceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class LocationInitializer implements BaseInitializer {

    private final LocationYaml locationYaml;
    private final LocalLevelRepository localLevelRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    @Override
    @Transactional
    public void initialize() {
        log.info("************ Initializing Locations ************");

        if (provinceRepository.count() > 0) {
            log.info("Locations already initialized, skipping...");
            return;
        }

        for (LocationYaml.Provinces p : locationYaml.getProvinces()) {

            Province province = new Province();
            province.setName(p.getName());

            Province savedProvince = provinceRepository.save(province);

            for (LocationYaml.District d : p.getDistricts()) {
                District district = new District();
                district.setName(d.getName());
                district.setProvince(savedProvince);

                District savedDistrict = districtRepository.save(district);

                for (LocationYaml.LocalLevel l : d.getLocalLevels()) {
                    LocalLevel localLevel = new LocalLevel();
                    localLevel.setName(l.getName());
                    localLevel.setDistrict(savedDistrict);

                    localLevelRepository.save(localLevel);
                }
            }
        }

        log.info("************ Location Initialization Completed ************");
    }
}
