package com.trek.trekker.service;

import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.admin.service.MediaService;
import com.trek.core.constants.DestinationReviewStatus;
import com.trek.core.dto.request.PaginationRequest;
import com.trek.core.dto.response.DestinationRatingStatusResponse;
import com.trek.core.entity.Destination;
import com.trek.core.entity.DestinationReview;
import com.trek.core.entity.Media;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.exception.UnauthorizedException;
import com.trek.core.repository.DestinationReviewRepository;
import com.trek.core.util.PaginationUtil;
import com.trek.core.util.RequestValidator;
import com.trek.trekker.dto.request.ReviewRequest;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DestinationReviewService {

    private final TrekkerSecurityContextHolderHolder securityContextHolderHolder;
    private final DestinationQueryService destinationQueryService;
    private final DestinationReviewRepository destinationReviewRepository;
    private final MediaService mediaService;
    private final MediaMappingService mediaMappingService;


    @Transactional(propagation = Propagation.REQUIRED)
    public DestinationReview review(ReviewRequest request) {
        List<Media> media = mediaService.finaAllByUrl(request.getImages());
        Destination destination = destinationQueryService.getById(request.getDestination());
        DestinationReview review = new DestinationReview();
        review.setTrekker(securityContextHolderHolder.getCurrentUser());
        review.setMessage(request.getMessage());
        review.setRating(request.getRating());
        review.setDestination(destination);
        review.setStatus(DestinationReviewStatus.ACTIVE);
        DestinationReview savedReview = destinationReviewRepository.save(review);
        mediaMappingService.map(media, savedReview);
        return savedReview;
    }

    @Transactional(readOnly = true)
    public Page<DestinationReview> getAll(PaginationRequest request, Long destinationId) {
        Destination destination = destinationQueryService.getById(destinationId);
        return destinationReviewRepository.findAllByDestination(destination, PaginationUtil.of(request));
    }

    @Transactional(readOnly = true)
    public DestinationRatingStatusResponse getStatus(Long destinationId) {
        return destinationReviewRepository.getRatingStatsByDestinationId(destinationId);
    }

    @Transactional
    public void delete(Long reviewId) {
        DestinationReview review = destinationReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RequestValidationException("Invalid review id"));
        RequestValidator.Validation.of(review.getTrekker().equals(securityContextHolderHolder.getCurrentUser()))
                .orElseThrow(() -> new UnauthorizedException("You can't delete other's review"));
        review.setStatus(DestinationReviewStatus.DELETED);
        destinationReviewRepository.save(review);
    }
}
