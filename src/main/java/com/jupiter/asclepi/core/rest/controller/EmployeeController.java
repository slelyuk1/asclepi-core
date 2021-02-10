package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.people.EmployeeInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface EmployeeController extends Crud<Integer, CreateEmployeeRequest, EditEmployeeRequest, EmployeeInfo> {

    @Override
    @PostMapping("/create")
    EmployeeInfo create(@RequestBody CreateEmployeeRequest createRequest);

    @Override
    @DeleteMapping("/{id}/delete")
    void delete(@PathVariable("id") Integer id);

    @Override
    @PostMapping("/edit")
    EmployeeInfo edit(@RequestBody EditEmployeeRequest editRequest);

    @Override
    @GetMapping("/{id}")
    EmployeeInfo getOne(@PathVariable("id") Integer id);

    @Override
    @GetMapping("/all")
    List<EmployeeInfo> getAll();

    @GetMapping("/")
    EmployeeInfo getOne();
}
