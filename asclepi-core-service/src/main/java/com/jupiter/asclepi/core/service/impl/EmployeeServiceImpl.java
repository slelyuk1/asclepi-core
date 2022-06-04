package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.EmployeeRepository;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.service.exception.employee.LoginNotUniqueException;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;
import com.jupiter.asclepi.core.service.util.IdGeneratorUtils;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Validated
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final ConversionService conversionService;
    private final EmployeeRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Employee create(@Valid @NotNull CreateEmployeeRequest createRequest) {
        Employee toCreate = Objects.requireNonNull(conversionService.convert(createRequest, Employee.class));
        if (employeeWithLoginExists(createRequest.getLogin())) {
            throw new LoginNotUniqueException(createRequest.getLogin());
        }
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        toCreate.setId(IdGeneratorUtils.generateId().intValue());
        return repository.save(Objects.requireNonNull(toCreate));
    }

    @Override
    public Boolean delete(@Valid @NotNull Integer toDeleteId) {
        return repository.findById(toDeleteId)
                .map(toDelete -> {
                    repository.delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Try<Employee> edit(@Valid @NotNull EditEmployeeRequest editRequest) {
        return Try.of(() -> {
            Employee existing = repository.findById(editRequest.getId())
                    .orElseThrow(() -> new NonExistentIdException("Employee", editRequest.getId()));
            Employee toCopyFrom = Objects.requireNonNull(conversionService.convert(editRequest, Employee.class));
            String newLogin = toCopyFrom.getLogin();
            if (!existing.getLogin().equals(newLogin) && employeeWithLoginExists(newLogin)) {
                throw new LoginNotUniqueException(newLogin);
            }
            CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, existing);
            return repository.save(existing);
        });
    }

    @Override
    public Optional<Employee> getOne(@Valid @NotNull Integer employeeId) {
        return repository.findById(employeeId);
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    private boolean employeeWithLoginExists(String login) {
        Employee employeeWithLogin = new Employee();
        employeeWithLogin.setLogin(login);
        return repository.exists(Example.of(employeeWithLogin));
    }

    @Override
    public Optional<Employee> findEmployeeByLogin(@NotNull String login) {
        Employee toSearch = new Employee();
        toSearch.setLogin(login);
        return repository.findOne(Example.of(toSearch));
    }
}
