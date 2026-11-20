package com.trek.core.constants;

public enum AdminStatus {
    ACTIVE(1, "ACTIVE"),
    INACTIVE(2, "INACTIVE"),
    DELETED(3, "DELETED"),
    BLOCKED(4, "BLOCKED"),
    PENDING(5, "PENDING");

    private String name;
    private Integer id;

    AdminStatus(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getStatus() {
        return name;
    }

    public Integer getId() {
        return id;
    }

}
