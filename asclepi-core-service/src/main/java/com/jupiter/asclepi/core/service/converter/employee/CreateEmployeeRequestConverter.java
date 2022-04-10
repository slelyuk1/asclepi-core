package com.jupiter.asclepi.core.service.converter.employee;

import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.model.request.people.CreateEmployeeRequest;
import org.springframework.core.convert.converter.Converter;

public class CreateEmployeeRequestConverter implements Converter<CreateEmployeeRequest, Employee> {
    @Override
    public Employee convert(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setLogin(request.getLogin());
        employee.setPassword(request.getPassword());
        employee.setName(request.getName());
        employee.setSurname(request.getSurname());
        employee.setMiddleName(request.getMiddleName());
        employee.setRole(request.getRole());
        employee.setAdditionalInfo(request.getAdditionalInfo());
        return employee;
    }
}
