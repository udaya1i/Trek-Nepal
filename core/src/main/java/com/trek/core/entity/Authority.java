package com.trek.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author udaya
 */

@Entity
@Getter
@Setter
public class Authority extends BaseEntity {

    @Column(name = "name")
    private String code;

    @Column(name = "description")
    private String description;

}
