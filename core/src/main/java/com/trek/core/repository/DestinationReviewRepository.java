package com.trek.core.repository;

import com.trek.core.dto.response.DestinationRatingStatusResponse;
import com.trek.core.entity.Destination;
import com.trek.core.entity.DestinationReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationReviewRepository extends AbstractRepository<DestinationReview, Long> {

    public Page<DestinationReview> findAllByDestination(Destination destination, Pageable pageable);

    @Query("SELECT COUNT(dr) as totalReviews, AVG(dr.rating) as averageRating, dr.destination.id as destinationId " +
            "FROM DestinationReview dr WHERE dr.destination.id = :destinationId " +
            "GROUP BY dr.destination.id")
    DestinationRatingStatusResponse getRatingStatsByDestinationId(@Param("destinationId") Long destinationId);

}
