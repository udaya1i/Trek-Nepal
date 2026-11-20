package com.trek.core.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class District extends BaseEntity {

    @Column(name = "DISTRICT_NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(referencedColumnName = "id", name = "PROVINCE")
    private Province province;

    @Column(name = "DESCRIPTION")
    private String description;
}