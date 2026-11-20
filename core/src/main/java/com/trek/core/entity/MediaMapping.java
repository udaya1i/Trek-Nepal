package com.trek.core.entity;

import com.trek.core.constants.MediaOwnerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table
public class MediaMapping extends BaseEntity {

    @ManyToOne
    @JoinColumn
    private Media media;

    @Column
    private Long sequenceOrder;

    @Column
    private Long ownerId;

    @Column
    @Enumerated(EnumType.STRING)
    private MediaOwnerType mediaOwnerType;

}
