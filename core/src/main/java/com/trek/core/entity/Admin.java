package com.trek.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author udaya
 */

@Getter
@Setter
@Entity
public class Admin extends BaseEntity {

    @Column(name = "admin_full_name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_super_admin")
    private boolean isSuperAdmin;

    @Column(name = "password")
    private String password;

    @Column(name = "super_password")
    private String superPassword;

    @Column(name = "old_password")
    private String oldPassword;

    @Column(name = "password_change_date")
    private LocalDateTime passwordChangedDate;

    @Column(name = "password_attempt_count")
    private Long passwordAttemptCount;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Role role;

}
