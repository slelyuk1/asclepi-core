package com.jupiter.asclepi.core.service.converter.employee;

import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface RequestToRoleConverter extends Converter<Integer, Role> {

    @Override
    default Role convert(Integer roleId) {
        return Role.fromWithException(roleId);
    }

}
