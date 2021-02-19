package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.ServiceCrud;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import io.vavr.control.Try;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface EmployeeService extends ServiceCrud<Integer, CreateEmployeeRequest, EditEmployeeRequest, Employee, Boolean> {
    @Override
    Try<Employee> create(@Valid @NotNull CreateEmployeeRequest createRequest);

    @Override
    Boolean delete(@NotNull Integer deleteRequest);

    @Override
    Try<Employee> edit(@Valid @NotNull EditEmployeeRequest editRequest);

    @Override
    Optional<Employee> getOne(@NotNull Integer getRequest);

    @Override
    List<Employee> getAll();
}
