package com.jupiter.asclepi.core.service.converter.employee;

import com.jupiter.asclepi.core.repository.entity.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToEmployeeConverter extends Converter<CreateEmployeeRequest, Employee> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Employee convert(CreateEmployeeRequest request);

}
