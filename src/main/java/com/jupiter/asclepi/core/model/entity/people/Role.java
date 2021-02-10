package com.jupiter.asclepi.core.model.entity.people;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Role {
    ADMIN(1), DOCTOR(2);
    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Optional<Role> from(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.id == id)
                .findAny();
    }
}
