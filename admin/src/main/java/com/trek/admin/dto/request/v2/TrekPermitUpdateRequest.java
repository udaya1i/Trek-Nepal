package com.trek.admin.dto.request.v2;

import com.trek.admin.dto.request.TrekPermitAddRequest;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class TrekPermitUpdateRequest implements Request {

    private Long id;
    private String name;
    private String slug;
    private String permitCode;
    private String description;
    private String issuingAuthority;
    private String validityPeriod;
    private String applicationProcess;
    private List<String> requiredDocuments;
    private List<TrekPermitAddRequest.PermitPricingRequest> pricingRules;
    private Boolean doesPermitExpire;

}
