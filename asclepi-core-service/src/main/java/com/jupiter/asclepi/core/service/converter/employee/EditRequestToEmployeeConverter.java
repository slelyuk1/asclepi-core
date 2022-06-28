package com.jupiter.asclepi.core.service.converter.employee;

import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EditRequestToEmployeeConverter extends Converter<EditEmployeeRequest, Employee> {

    @Override
    @Mapping(target = "role", source = "roleId")
    @Mapping(target = "creation", expression = "java(null)")
    Employee convert(EditEmployeeRequest request);

}
