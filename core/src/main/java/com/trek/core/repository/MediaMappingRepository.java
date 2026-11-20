package com.trek.core.repository;

import com.trek.core.constants.MediaOwnerType;
import com.trek.core.entity.MediaMapping;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaMappingRepository extends AbstractRepository<MediaMapping, Long> {

    public List<MediaMapping> findAllByOwnerIdAndMediaOwnerType(Long ownerId, MediaOwnerType mediaOwnerType);
    public List<MediaMapping> findAllByOwnerIdInAndMediaOwnerType(List<Long> ownerId, MediaOwnerType mediaOwnerType);

}
