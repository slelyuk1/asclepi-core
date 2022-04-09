package com.jupiter.asclepi.core.service.impl.employee.converter;

import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.response.people.EmployeeInfo;
import org.springframework.core.convert.converter.Converter;

public class EmployeeConverter implements Converter<Employee, EmployeeInfo> {
    @Override
    public EmployeeInfo convert(Employee employee) {
        return new EmployeeInfo(
                employee.getId(),
                employee.getLogin(),
                employee.getRole(),
                employee.getName(),
                employee.getSurname(),
                employee.getMiddleName(),
                employee.getAdditionalInfo()
        );
    }
}
