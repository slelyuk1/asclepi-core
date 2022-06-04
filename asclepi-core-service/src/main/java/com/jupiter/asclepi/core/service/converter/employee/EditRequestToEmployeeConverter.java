package com.jupiter.asclepi.core.service.converter.employee;

import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.entity.Employee;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EditRequestToEmployeeConverter extends Converter<EditEmployeeRequest, Employee> {

    @Override
    @Mapping(target = "role", source = "roleId")
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Employee convert(EditEmployeeRequest request);

}
