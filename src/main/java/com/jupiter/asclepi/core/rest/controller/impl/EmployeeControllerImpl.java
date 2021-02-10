package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.people.EmployeeInfo;
import com.jupiter.asclepi.core.rest.controller.EmployeeController;
import com.jupiter.asclepi.core.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService service;

    @Override
    public EmployeeInfo create(CreateEmployeeRequest createRequest) {
        Employee employee = service.create(createRequest);
        return new EmployeeInfo(employee.getId(), employee.getLogin(), employee.getRole().getId(), employee.getName(), employee.getSurname(),
                employee.getMiddleName(), employee.getAdditionalInfo());
    }

    @Override
    public void delete(Integer deleteRequest) {
        throw new UnsupportedOperationException("This operation is not implemented yet");
    }

    @Override
    public EmployeeInfo edit(EditEmployeeRequest editRequest) {
        throw new UnsupportedOperationException("This operation is not implemented yet");
    }

    @Override
    public EmployeeInfo getOne(Integer getRequest) {
        throw new UnsupportedOperationException("This operation is not implemented yet");
    }

    @Override
    public List<EmployeeInfo> getAll() {
        throw new UnsupportedOperationException("This operation is not implemented yet");
    }

    @Override
    public EmployeeInfo getOne() {
        throw new UnsupportedOperationException("This operation is not implemented yet");
    }
}
