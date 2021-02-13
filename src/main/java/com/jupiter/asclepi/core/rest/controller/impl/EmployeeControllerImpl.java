package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.people.EmployeeInfo;
import com.jupiter.asclepi.core.rest.controller.EmployeeController;
import com.jupiter.asclepi.core.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
// todo erroneous situations
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;
    private final ConversionService conversionService;

    @Override
    public EmployeeInfo create(CreateEmployeeRequest createRequest) {
        Employee employee = employeeService.create(createRequest);
        return conversionService.convert(employee, EmployeeInfo.class);
    }

    @Override
    public ResponseEntity<?> delete(Integer toDeleteId) {
        boolean result = employeeService.delete(toDeleteId);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Override
    public EmployeeInfo edit(EditEmployeeRequest editRequest) {
        Employee employee = employeeService.edit(editRequest);
        return conversionService.convert(employee, EmployeeInfo.class);
    }

    @Override
    public ResponseEntity<EmployeeInfo> getOne(Integer employeeId) {
        return employeeService.getOne(employeeId)
                .map(employee -> conversionService.convert(employee, EmployeeInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public List<EmployeeInfo> getAll() {
        return employeeService.getAll().stream()
                .map(employee -> conversionService.convert(employee, EmployeeInfo.class))
                .collect(Collectors.toList());

    }

    @Override
    public EmployeeInfo getOne() {
        throw new UnsupportedOperationException("This functionality is not implemented yet!");
    }
}
