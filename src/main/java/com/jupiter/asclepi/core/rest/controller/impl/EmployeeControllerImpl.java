package com.jupiter.asclepi.core.rest.controller.impl;

import com.jupiter.asclepi.core.exception.AsclepiRuntimeException;
import com.jupiter.asclepi.core.exception.employee.LoginNotUniqueException;
import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.error.ErrorInfo;
import com.jupiter.asclepi.core.model.response.people.EmployeeInfo;
import com.jupiter.asclepi.core.rest.controller.EmployeeController;
import com.jupiter.asclepi.core.rest.controller.util.ControllerUtils;
import com.jupiter.asclepi.core.service.EmployeeService;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<?> create(CreateEmployeeRequest createRequest) {
        Try<ResponseEntity<?>> creationTry = employeeService.create(createRequest).map(employee -> {
            EmployeeInfo employeeInfo = conversionService.convert(employee, EmployeeInfo.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeInfo);
        });
        return creationTry
                .recover(LoginNotUniqueException.class, e -> ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getMessage())))
                .onFailure(e -> log.error("An error occurred during employee creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
    }

    @Override
    public ResponseEntity<?> delete(Integer toDeleteId) {
        boolean result = employeeService.delete(toDeleteId);
        return result ? ResponseEntity.ok().build() : ControllerUtils.notFoundResponse();
    }

    @Override
    public ResponseEntity<?> edit(EditEmployeeRequest editRequest) {
        Try<ResponseEntity<?>> editionTry = employeeService.edit(editRequest).map(edited -> {
            EmployeeInfo employeeInfo = conversionService.convert(edited, EmployeeInfo.class);
            return ResponseEntity.ok().body(employeeInfo);
        });
        return editionTry
                .recover(NonExistentIdException.class, e -> ControllerUtils.notFoundResponse())
                .recover(LoginNotUniqueException.class, e -> ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo(e.getMessage())))
                .onFailure(e -> log.error("An error occurred during employee creation: ", e))
                .getOrElseThrow(AsclepiRuntimeException::new);
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
    public EmployeeInfo getOne() {
        // todo implement when security features will be implemented
        throw new UnsupportedOperationException("This functionality is not implemented yet!");
    }
}
