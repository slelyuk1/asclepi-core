package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;

public interface EmployeeService extends CrudService<Integer, CreateEmployeeRequest, EditEmployeeRequest, Employee, Boolean> {
}
