package com.jupiter.asclepi.core.model.other;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Role {
    ADMIN(1, "Administrator"), DOCTOR(2, "Doctor");

    private static final String ROLE_NOT_EXISTS_MESSAGE = "Role with id %d doesn't exist!";
    private final int id;
    private final String roleName;

    Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @JsonValue
    public int getId() {
        return id;
    }

    public static Optional<Role> from(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.id == id)
                .findAny();
    }

    @JsonCreator
    public static Role fromWithException(int id) throws IllegalArgumentException {
        return from(id).orElseThrow(() -> new IllegalArgumentException(String.format(ROLE_NOT_EXISTS_MESSAGE, id)));
    }
}
