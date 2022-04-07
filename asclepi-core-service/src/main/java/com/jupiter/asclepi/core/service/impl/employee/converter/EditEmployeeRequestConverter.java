package com.jupiter.asclepi.core.service.impl.employee.converter;

import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import org.springframework.core.convert.converter.Converter;

public class EditEmployeeRequestConverter implements Converter<EditEmployeeRequest, Employee> {
    @Override
    public Employee convert(EditEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setId(request.getId());
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
