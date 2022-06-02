package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.repository.entity.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface EmployeeService extends CrudService<Integer, CreateEmployeeRequest, EditEmployeeRequest, Employee, Boolean> {
    Optional<Employee> findEmployeeByLogin(@NotNull String login);
}
