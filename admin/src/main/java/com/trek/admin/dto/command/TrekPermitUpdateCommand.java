package com.trek.admin.dto.command;

import com.trek.core.constants.NationalityCategory;
import com.trek.core.dto.command.Command;
import com.trek.core.entity.Permit;
import lombok.Data;

import java.util.List;

@Data
public class TrekPermitUpdateCommand implements Command {

    private Permit permit;
    private String name;
    private String slug;
    private String permitCode;
    private String description;
    private String issuingAuthority;
    private String validityPeriod;
    private String applicationProcess;
    private List<String> requiredDocuments;
    private List<PermitPricingCommand> pricingRules;
    private Boolean doesPermitExpire;

    @Data
    public static class PermitPricingCommand {

        private NationalityCategory applicableFor;
        private Double price;
        private String currency;
        private String validFrom;
        private String validTo;
        private String specialConditions;
    }
}