package com.trek.core.entity;

import com.trek.core.constants.MediaOwnerType;
import com.trek.core.constants.TrekkerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author udaya
 */
@Getter
@Setter
@Entity
public class Trekker extends BaseEntity implements MediaOwner {

    @Column(name = "trekker_full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "location")
    private String location;

    @Column(name = "is_verified")
    private String isVerified;

    @Column(name = "password")
    private String password;

    @Column(name = "old_password")
    private String oldPassword;

    @Column(name = "password_attempt_count")
    private Long passwordAttemptCount;

    @Column(name = "account_locked")
    private Boolean accountLocked = Boolean.FALSE;

    @Column(name = "password_changed_date")
    private LocalDateTime passwordChangedDate;

    @JoinColumn(referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TrekkerStatus status;

    @Column(name = "google_id", unique = true)
    private String googleId;

    @Column(name = "provider")
    private String provider = "LOCAL";

    @Override
    public MediaOwnerType getOwnerType() {
        return MediaOwnerType.TREKKER;
    }
}
