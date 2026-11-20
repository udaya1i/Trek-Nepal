package com.trek.core.entity;

import com.trek.core.constants.SluggableType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "slug_history", uniqueConstraints = @UniqueConstraint(columnNames = {"owner_type", "old_slug"}))
public class SlugHistory extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type", nullable = false)
    private SluggableType ownerType;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "old_slug", nullable = false)
    private String oldSlug;
}
