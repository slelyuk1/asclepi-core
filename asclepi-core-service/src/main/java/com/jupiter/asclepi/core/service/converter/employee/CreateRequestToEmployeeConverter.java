package com.jupiter.asclepi.core.service.converter.employee;

import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToEmployeeConverter extends Converter<CreateEmployeeRequest, Employee> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdWhen", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Employee convert(@Nullable CreateEmployeeRequest request);

}
