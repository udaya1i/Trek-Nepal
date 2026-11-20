package com.trek.trekker.dto.request.requestValidator;

import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.admin.service.MediaService;
import com.trek.core.dto.request.ImageRequest;
import com.trek.core.entity.Destination;
import com.trek.core.entity.Media;
import com.trek.core.util.RequestValidator;
import com.trek.trekker.dto.command.TrekkerStoryAddCommand;
import com.trek.trekker.dto.request.TrekkerStoryAddRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TrekStoryRequestValidator implements RequestValidator<TrekkerStoryAddRequest, TrekkerStoryAddCommand> {

    private final DestinationQueryService destinationQueryService;
    private final MediaService mediaService;


    @Override
    public TrekkerStoryAddCommand validate(TrekkerStoryAddRequest request) {

        TrekkerStoryAddCommand command = new TrekkerStoryAddCommand();
        if (request.getDestination() != null) {
            Destination destination = destinationQueryService.getById(request.getDestination());
            command.setDestination(destination);
        }
        List<Media> media = mediaService.findAllByImageIdentifier(request.getImages().stream().map(ImageRequest::getPublicUrl).toList());
        command.setImages(media);
        command.setTitle(request.getTitle());
        command.setShortDescription(request.getShortDescription());
        command.setContent(request.getContent());
        command.setCompletedAt(request.getCompletedAt());
        command.setDurationDays(request.getDurationDays());
        command.setHighlights(request.getHighlights());
        command.setTags(request.getTags());
        return command;
    }
}
