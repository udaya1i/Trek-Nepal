package com.trek.core.constants;

import lombok.Getter;

public enum DifficultyLevel {

    EASY("Suitable for beginners and casual trekkers", "Easy"),
    MODERATE("Requires some experience and moderate fitness", "Moderate"),
    HARD("Challenging trekStrategy for experienced hikers", "Hard"),
    EXTREME("Very demanding trekStrategy for highly experienced trekkers", "Extreme");

    @Getter
    private final String description;

    @Getter
    private final String name;

    DifficultyLevel(String description, String name) {
        this.description = description;
        this.name = name;
    }

}
