package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface EmployeeService extends CrudService<Integer, CreateEmployeeRequest, EditEmployeeRequest, Employee, Integer> {

    @Override
    default Class<Employee> getEntityClass() {
        return Employee.class;
    }

    @Override
    default Class<Integer> getIdClass() {
        return Integer.class;
    }

    Optional<Employee> findEmployeeByLogin(@NotNull String login);

}
