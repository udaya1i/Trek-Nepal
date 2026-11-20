package com.trek.core.constants;

import lombok.Getter;

@Getter
public enum MunicipalityType {

    METROPOLITAN_CITY(1, "Metropolitan City"),
    SUB_METROPOLITAN_CITY(2, "Sub-Metropolitan City"),
    MUNICIPALITY(3, "Municipality"),
    RURAL_MUNICIPALITY(4, "Rural Municipality");

    private final int id;
    private final String displayName;

    MunicipalityType(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

}
