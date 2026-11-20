package com.trek.core.entity;

import com.trek.core.constants.MediaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "media")
public class Media extends BaseEntity {

    @Column(name = "secure_url")
    private String imagePublicUrl;

    @Column(name = "public_id")
    private String publicId;

    @Column(length = 1000, name = "description")
    private String description;

    @Column(name = "image_alt_message")
    private String imageAltMessage;

    @Column(name = "image_sequence")
    private Long sequence;

    @Column(name = "status")
    private MediaStatus status;

    @Column(name = "image_identifier")
    public String imageIdentifier;

    @Column(name = "owner_id")
    public Long ownerId;

}
