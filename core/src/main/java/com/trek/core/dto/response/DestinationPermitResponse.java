package com.trek.core.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.trek.core.entity.Permit;
import com.trek.core.entity.PermitPricing;
import com.trek.core.constants.NationalityCategory;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DestinationPermitResponse {

    private Long id;
    private String name;
    private String slug;
    private String permitCode;
    private String description;
    private String issuingAuthority;
    private String validityPeriod;
    private String applicationProcess;
    private List<String> requiredDocuments;
    private List<PermitPricingResponse> pricingRules;
    private Boolean doesPermitExpire;

    public static List<DestinationPermitResponse> of(List<Permit> permit) {
        return permit.stream().map(DestinationPermitResponse::of).toList();
    }

    public static DestinationPermitResponse of(Permit permit) {
        DestinationPermitResponse response = new DestinationPermitResponse();
        response.setId(permit.getId());
         response.setName(permit.getName());
        response.setSlug(permit.getSlug());
        response.setPermitCode(permit.getPermitCode());
        response.setDescription(permit.getDescription());
        response.setIssuingAuthority(permit.getIssuingAuthority());
        response.setApplicationProcess(permit.getApplicationProcess());
        response.setRequiredDocuments(permit.getRequiredDocuments());
        response.setValidityPeriod(permit.getValidityPeriod());
        response.setPricingRules(permit.getPricingRules().stream().map(PermitPricingResponse::of).toList());
        return response;
    }

    @Data
    static class PermitPricingResponse {

        private NationalityCategory applicableFor;
        private Double price;
        private String currency;
        private String validFrom;
        private String validTo;
        private String specialConditions;

        public static PermitPricingResponse of(PermitPricing permitPricing) {
            PermitPricingResponse response = new PermitPricingResponse();
            response.setApplicableFor(permitPricing.getApplicableFor());
            response.setPrice(permitPricing.getPrice());
            response.setCurrency(permitPricing.getCurrency());
            response.setValidFrom(permitPricing.getValidFrom());
            response.setValidTo(permitPricing.getValidTo());
            response.setSpecialConditions(response.getSpecialConditions());
            return response;
        }
    }

}
