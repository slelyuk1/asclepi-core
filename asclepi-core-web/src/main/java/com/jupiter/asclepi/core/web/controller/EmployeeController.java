package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.EmployeeInfo;
import com.jupiter.asclepi.core.web.helper.api.crud.CrudUsingPathVariableController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface EmployeeController extends CrudUsingPathVariableController<Integer, CreateEmployeeRequest, EditEmployeeRequest, EmployeeInfo> {

    @GetMapping("/")
    ResponseEntity<EmployeeInfo> getOne();
}
