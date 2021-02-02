package com.jupiter.asclepi.core.model.entity.people;

import lombok.Getter;

@Getter
public enum Role {
    ADMINISTRATOR("Administrator"), DOCTOR("Doctor");

    private final String name;
    private String description;

    Role(String name) {
        this.name = name;
    }
}
