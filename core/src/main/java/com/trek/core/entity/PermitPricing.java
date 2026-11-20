package com.trek.core.entity;

import com.trek.core.constants.NationalityCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "permit_pricing")
public class PermitPricing extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "permit_id", nullable = false)
    private Permit permit;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicable_for")
    private NationalityCategory applicableFor;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "valid_from")
    private String validFrom;

    @Column(name = "valid_to")
    private String validTo;

    @Column(length = 3000, name = "special_conditions")
    private String specialConditions;
}