package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.model.response.people.EmployeeInfo;

public interface EmployeeController extends Crud<Integer, CreateEmployeeRequest, EditEmployeeRequest, EmployeeInfo> {

    EmployeeInfo getOne();
}
