package com.jupiter.asclepi.core.model.entity.people;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Role {
    ADMIN(1, "Administrator"), DOCTOR(2, "Doctor");
    private final int id;
    private final String roleName;

    Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public static Optional<Role> from(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.id == id)
                .findAny();
    }
}
