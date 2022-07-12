package com.jupiter.asclepi.core.web.controller.impl;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.EmployeeInfo;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.service.helper.api.CrudService;
import com.jupiter.asclepi.core.service.util.SecurityUtils;
import com.jupiter.asclepi.core.web.controller.EmployeeController;
import com.jupiter.asclepi.core.web.helper.api.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeControllerImpl extends AbstractController<EmployeeInfo> implements EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeControllerImpl(ConversionService conversionService, EmployeeService employeeService) {
        super(conversionService);
        this.employeeService = employeeService;
    }

    @Override
    public CrudService<Integer, CreateEmployeeRequest, EditEmployeeRequest, Employee, Integer> getCrudService() {
        return employeeService;
    }

    @Override
    public ResponseEntity<EmployeeInfo> getOne() {
        String login = SecurityUtils.getPrincipal(UserDetails.class)
                .map(UserDetails::getUsername)
                // todo normal exception
                .orElseThrow(() -> new IllegalStateException("Authentication must be present"));
        return employeeService.findEmployeeByLogin(login)
                .map(employee -> getConversionService().convert(employee, EmployeeInfo.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
