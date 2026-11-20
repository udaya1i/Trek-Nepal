package com.trek.core.entity;

import com.trek.core.constants.DestinationReviewStatus;
import com.trek.core.constants.MediaOwnerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DestinationReview extends BaseEntity implements MediaOwner {

    @Column(name = "rating")
    private Long rating;

    @Column(name = "message", length = 2000)
    private String message;

    @JoinColumn
    @ManyToOne
    private Destination destination;

    @JoinColumn
    @ManyToOne
    private Trekker trekker;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DestinationReviewStatus status;

    @Override
    public MediaOwnerType getOwnerType() {
        return MediaOwnerType.REVIEW;
    }
}
