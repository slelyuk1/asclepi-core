package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.exception.ErrorInfo;
import com.jupiter.asclepi.core.exception.LoginNotUniqueException;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.people.EmployeeInfo;
import com.jupiter.asclepi.core.rest.controller.EmployeeController;
import com.jupiter.asclepi.core.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // todo better usage of Try
    @Override
    public ResponseEntity<?> create(CreateEmployeeRequest createRequest) {
        return employeeService.create(createRequest)
                .map(employee -> conversionService.convert(employee, EmployeeInfo.class))
                .<ResponseEntity<?>>map(employeeInfo -> ResponseEntity.status(HttpStatus.CREATED).body(employeeInfo))
                .recover(LoginNotUniqueException.class, e -> ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getMessage())))
                .onFailure(ex -> log.error("An error occurred during employee creation: ", ex))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<?> delete(Integer toDeleteId) {
        boolean result = employeeService.delete(toDeleteId);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // todo erroneous situations
    @Override
    public ResponseEntity<EmployeeInfo> edit(EditEmployeeRequest editRequest) {
        return employeeService.edit(editRequest)
                .map(editedEmployee -> conversionService.convert(editedEmployee, EmployeeInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        // todo implement when security features will be implemented
        throw new UnsupportedOperationException("This functionality is not implemented yet!");
    }
}
