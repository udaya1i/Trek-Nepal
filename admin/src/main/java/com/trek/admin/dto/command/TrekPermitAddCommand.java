package com.trek.admin.dto.command;

import com.trek.core.entity.Destination;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.constants.NationalityCategory;
import com.trek.core.dto.command.Command;
import lombok.Data;

import java.util.List;

@Data
public class TrekPermitAddCommand implements Command {

    private Destination destination;
    private TrekStrategy trekStrategy;
    private String name;
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
