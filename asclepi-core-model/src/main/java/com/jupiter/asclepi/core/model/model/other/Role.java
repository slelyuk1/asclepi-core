package com.jupiter.asclepi.core.model.model.other;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Getter
public enum Role {
    ADMIN(1, "Administrator"), DOCTOR(2, "Doctor");

    private static final String ROLE_NOT_EXISTS_MESSAGE = "Role with id %d doesn't exist!";
    private final int id;
    private final String roleName;
    private final Collection<GrantedAuthority> authorities;

    Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
        authorities = Collections.singleton(new SimpleGrantedAuthority(roleName));
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

    @JsonValue
    public int getId() {
        return id;
    }
}
