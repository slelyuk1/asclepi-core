package com.jupiter.asclepi.core.service.converter.employee;

import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.response.people.EmployeeInfo;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EmployeeToInfoConverter extends Converter<Employee, EmployeeInfo> {

    @Override
    EmployeeInfo convert( Employee source);

}
