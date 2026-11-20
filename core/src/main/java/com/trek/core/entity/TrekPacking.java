package com.trek.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "trek_packings")
public class TrekPacking extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "trek_packing_items", joinColumns = @JoinColumn(name = "trek_packing_id"))
    @Column(name = "item", length = 2000)
    private List<String> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trek_strategy_id")
    private TrekStrategy trekStrategy;
}