package com.trek.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "permits")
public class Permit extends BaseEntity {

    @Column(nullable = false, name = "name", length = 1000)
    private String name;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "permit_code", length = 1000)
    private String permitCode;

    @Column(length = 10000, name = "description")
    private String description;

    @Column(name = "issuing_authority", length = 1000)
    private String issuingAuthority;

    @Column(name = "validity_period", length = 1000)
    private String validityPeriod;

    @Column(length = 5000, name = "application_process")
    private String applicationProcess;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "permit_required_documents", joinColumns = @JoinColumn(name = "permit_id"))
    @Column(name = "document", length = 2000)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> requiredDocuments;

    @OneToMany(mappedBy = "permit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<PermitPricing> pricingRules;

    @Column(name = "does_permit_expire")
    private Boolean doesPermitExpire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Destination destination;

}