package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface EmployeeService extends CrudService<Integer, CreateEmployeeRequest, EditEmployeeRequest, Employee, Boolean> {
    Optional<Employee> findEmployeeByLogin(@NotNull String login);
}
