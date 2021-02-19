package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.people.EmployeeInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface EmployeeController extends CrudController<Integer, CreateEmployeeRequest, EditEmployeeRequest, EmployeeInfo> {

    @Override
    @PostMapping("/create")
    ResponseEntity<?> create(@NotNull @RequestBody CreateEmployeeRequest createRequest);

    @Override
    @DeleteMapping("/{employeeId}")
    ResponseEntity<?> delete(@NotNull @PathVariable("employeeId") Integer id);

    @Override
    @PostMapping("/edit")
    ResponseEntity<?> edit(@NotNull @RequestBody EditEmployeeRequest editRequest);

    @Override
    @GetMapping("/{employeeId}")
    ResponseEntity<EmployeeInfo> getOne(@NotNull @PathVariable("employeeId") Integer employeeId);

    @Override
    @GetMapping("/all")
    List<EmployeeInfo> getAll();

    @GetMapping("/")
    EmployeeInfo getOne();
}
