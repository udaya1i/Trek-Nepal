package com.trek.core.admin.service;

import com.trek.core.constants.MediaOwnerType;
import com.trek.core.entity.Media;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.MediaOwner;
import com.trek.core.repository.MediaMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MediaMappingService {

    private final MediaMappingRepository mediaMappingRepository;
    private final MediaService mediaService;

    public <T extends MediaOwner> void map(List<Media> medias, T mediaOwner) {
        List<MediaMapping> mediaMappings = medias.stream()
                .map(media -> toMediaMapping(media, mediaOwner))
                .toList();
        mediaMappingRepository.saveAll(mediaMappings);
        mediaService.markAsActive(medias);
    }

    private <T extends MediaOwner> MediaMapping toMediaMapping(Media media, T mediaOwner) {
        MediaMapping mediaMapping = new MediaMapping();
        mediaMapping.setMediaOwnerType(mediaOwner.getOwnerType());
        mediaMapping.setOwnerId(mediaOwner.getId());
        mediaMapping.setMedia(media);
        mediaMapping.setSequenceOrder(media.getSequence());
        return mediaMapping;
    }

    public List<MediaMapping> getAllImage(Long ownerId, MediaOwnerType ownerType) {
        return mediaMappingRepository.findAllByOwnerIdAndMediaOwnerType(ownerId, ownerType);
    }

    public List<MediaMapping> getAllImage(List<Long> ownerId, MediaOwnerType ownerType) {
        return mediaMappingRepository.findAllByOwnerIdInAndMediaOwnerType(ownerId, ownerType);
    }
}
