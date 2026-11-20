package com.trek.admin.dto.request;

import com.trek.core.annotation.Required;
import com.trek.core.constants.NationalityCategory;
import com.trek.core.dto.request.Request;
import lombok.Data;

import java.util.List;

@Data
public class TrekPermitAddRequest implements Request {

    @Required(field = "Destination")
    private Long destination;
    private String name;
    private String permitCode;
    private String description;
    private String issuingAuthority;
    private String validityPeriod;
    private String applicationProcess;
    private List<String> requiredDocuments;
    private List<PermitPricingRequest> pricingRules;
    private Boolean doesPermitExpire;

    @Data
    public static class PermitPricingRequest {

        private NationalityCategory applicableFor;
        private Double price;
        private String currency;
        private String validFrom;
        private String validTo;
        private String specialConditions;
    }

}
