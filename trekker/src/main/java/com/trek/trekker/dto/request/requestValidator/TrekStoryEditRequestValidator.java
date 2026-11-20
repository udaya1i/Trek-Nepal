package com.trek.trekker.dto.request.requestValidator;

import com.trek.core.admin.service.MediaService;
import com.trek.core.dto.request.ImageRequest;
import com.trek.core.entity.Destination;
import com.trek.core.entity.Media;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.DestinationRepository;
import com.trek.core.repository.TrekStoryRepository;
import com.trek.core.util.RequestValidator;
import com.trek.trekker.dto.command.TrekkerStoryEditCommand;
import com.trek.trekker.dto.request.TrekkerStoryEditRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekStoryEditRequestValidator implements RequestValidator<TrekkerStoryEditRequest, TrekkerStoryEditCommand> {

    private final DestinationRepository destinationRepository;
    private final TrekStoryRepository storyRepository;
    private final MediaService mediaService;

    @Override
    public TrekkerStoryEditCommand validate(TrekkerStoryEditRequest request) {

        TrekkerStoryEditCommand command = new TrekkerStoryEditCommand();

        storyRepository.findById(request.getId())
                .orElseThrow(() -> new RequestValidationException("Invalid story"));
        List<Media> media = mediaService.findAllByImageIdentifier(request.getImages().stream().map(ImageRequest::getPublicUrl).toList());

        command.setImages(media);
        command.setId(request.getId());
        command.setTitle(request.getTitle());
        command.setSlug(request.getSlug());
        command.setShortDescription(request.getShortDescription());
        command.setContent(request.getContent());
        command.setCompletedAt(request.getCompletedAt());
        command.setDurationDays(request.getDurationDays());
        command.setIsDraft(request.getIsDraft());
        command.setHighlights(request.getHighlights());
        command.setTags(request.getTags());

        if (request.getDestinationId() != null) {
            Destination destination = destinationRepository.findById(request.getDestinationId())
                    .orElseThrow(() -> new RequestValidationException("Destination not found"));
            command.setDestination(destination);
        }

        return command;
    }
}
