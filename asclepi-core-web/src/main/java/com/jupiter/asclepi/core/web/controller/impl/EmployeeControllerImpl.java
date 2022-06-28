package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.EmployeeInfo;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.service.api.AuthenticationService;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.service.util.SecurityUtils;
import com.jupiter.asclepi.core.web.controller.EmployeeController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;
    private final AuthenticationService authenticationService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<EmployeeInfo> create(CreateEmployeeRequest createRequest) {
        Employee employee = employeeService.create(createRequest);
        EmployeeInfo employeeInfo = conversionService.convert(employee, EmployeeInfo.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeInfo);
    }

    @Override
    public ResponseEntity<Void> delete(Integer toDeleteId) {
        boolean result = employeeService.delete(toDeleteId);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<EmployeeInfo> edit(EditEmployeeRequest editRequest) {
        Employee edited = employeeService.edit(editRequest);
        EmployeeInfo employeeInfo = conversionService.convert(edited, EmployeeInfo.class);
        return ResponseEntity.ok().body(employeeInfo);
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
                .toList();
    }

    @Override
    public ResponseEntity<EmployeeInfo> getOne() {
        String login = SecurityUtils.getPrincipal(UserDetails.class)
                .map(UserDetails::getUsername)
                // todo normal exception
                .orElseThrow(() -> new IllegalStateException("Authentication must be present"));
        return employeeService.findEmployeeByLogin(login)
                .map(employee -> conversionService.convert(employee, EmployeeInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
