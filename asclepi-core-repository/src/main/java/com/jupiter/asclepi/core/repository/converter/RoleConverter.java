package com.jupiter.asclepi.core.repository.converter;

import com.jupiter.asclepi.core.repository.entity.employee.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// todo move this class
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Integer roleId) {
        return Role.fromWithException(roleId);
    }
}
