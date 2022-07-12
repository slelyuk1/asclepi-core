package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.EmployeeInfo;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.web.helper.api.CrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EmployeeController extends CrudController<Employee, Integer, Integer, CreateEmployeeRequest, EditEmployeeRequest, EmployeeInfo> {

    @Override
    default Class<EmployeeInfo> getResponseClass() {
        return EmployeeInfo.class;
    }

    @Override
    default EmployeeInfo create(@RequestBody CreateEmployeeRequest createRequest) {
        return CrudController.super.create(createRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    default void delete(@PathVariable("id") Integer deleteRequest) {
        CrudController.super.delete(deleteRequest);
    }

    @Override
    default List<EmployeeInfo> getAll() {
        return CrudController.super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    default EmployeeInfo getOne(@PathVariable("id") Integer getRequest) {
        return CrudController.super.getOne(getRequest);
    }

    @Override
    default EmployeeInfo edit(@RequestBody EditEmployeeRequest editRequest) {
        return CrudController.super.edit(editRequest);
    }

    @GetMapping("/")
    ResponseEntity<EmployeeInfo> getOne();

}
