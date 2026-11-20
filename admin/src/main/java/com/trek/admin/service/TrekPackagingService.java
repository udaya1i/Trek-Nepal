package com.trek.admin.service;

import com.trek.admin.dto.command.TrekPackagingAddCommand;
import com.trek.admin.dto.command.TrekPackagingUpdateCommand;
import com.trek.core.entity.TrekPacking;
import com.trek.core.repository.TrekPackagingRepository;
import com.trek.core.admin.service.TrekStrategyQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekPackagingService {

    private final TrekPackagingRepository trekPackagingRepository;
    private final TrekStrategyQueryService trekStrategyQueryService;

    public TrekPacking add(TrekPackagingAddCommand command) {
        TrekPacking packing = new TrekPacking();
        packing.setTrekStrategy(command.getTrekStrategy());
        packing.setItems(command.getItems());
        packing.setName(command.getName());
        return trekPackagingRepository.save(packing);
    }

    public TrekPacking update(TrekPackagingUpdateCommand command) {
        TrekPacking packing = command.getTrekPacking();
        packing.setName(command.getName());
        packing.setItems(command.getItems());
        return trekPackagingRepository.save(packing);
    }

    public List<TrekPacking> getByTrekStrategy(Long id) {
        return trekPackagingRepository.getByTrekStrategy(trekStrategyQueryService.getById(id));
    }
}
