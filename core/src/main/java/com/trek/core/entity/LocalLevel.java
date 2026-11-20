package com.trek.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "locallevel")
public class LocalLevel extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private District district;

}
