package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.EmployeeInfo;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.web.controller.EmployeeController;
import com.jupiter.asclepi.core.web.util.ControllerUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<EmployeeInfo> create(CreateEmployeeRequest createRequest) {
        Employee employee = employeeService.create(createRequest);
        EmployeeInfo employeeInfo = conversionService.convert(employee, EmployeeInfo.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeInfo);
    }

    @Override
    public ResponseEntity<?> delete(Integer toDeleteId) {
        boolean result = employeeService.delete(toDeleteId);
        return result ? ResponseEntity.ok().build() : ControllerUtils.notFoundResponse();
    }

    @Override
    public ResponseEntity<?> edit(EditEmployeeRequest editRequest) {
        Employee edited = employeeService.edit(editRequest);
        EmployeeInfo employeeInfo = conversionService.convert(edited, EmployeeInfo.class);
        return ResponseEntity.ok().body(employeeInfo);
    }

    @Override
    public ResponseEntity<EmployeeInfo> getOne(Integer employeeId) {
        return employeeService.getOne(employeeId)
                .map(employee -> conversionService.convert(employee, EmployeeInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }

    @Override
    public List<EmployeeInfo> getAll() {
        return employeeService.getAll().stream()
                .map(employee -> conversionService.convert(employee, EmployeeInfo.class))
                .collect(Collectors.toList());

    }

    @Override
    public ResponseEntity<EmployeeInfo> getOne() {
        String login = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return employeeService.findEmployeeByLogin(login)
                .map(employee -> conversionService.convert(employee, EmployeeInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ControllerUtils.notFoundResponse());
    }
}
