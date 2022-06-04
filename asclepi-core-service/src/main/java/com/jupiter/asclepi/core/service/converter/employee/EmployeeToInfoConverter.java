package com.jupiter.asclepi.core.service.converter.employee;

import com.jupiter.asclepi.core.model.response.EmployeeInfo;
import com.jupiter.asclepi.core.repository.entity.Employee;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EmployeeToInfoConverter extends Converter<Employee, EmployeeInfo> {

    @Override
    @Mapping(target = "roleId", source = "role.id")
    EmployeeInfo convert(Employee source);

}
