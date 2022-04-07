package com.jupiter.asclepi.core.model.model.entity.converter;

import com.jupiter.asclepi.core.model.model.other.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {
    private static final String ROLE_NOT_EXISTS_MESSAGE = "Role with id %d doesn't exist!";

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Integer roleId) {
        return Role.fromWithException(roleId);
    }
}
