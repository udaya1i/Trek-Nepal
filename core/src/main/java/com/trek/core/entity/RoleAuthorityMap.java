package com.trek.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author udaya
 */

@Entity
@Getter
@Setter
public class RoleAuthorityMap extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority", referencedColumnName = "id")
    private Authority authority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    @Column(name = "is_active")
    private boolean isActive;

}
