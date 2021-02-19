package com.jupiter.asclepi.core.service.impl.employee;

import com.jupiter.asclepi.core.exception.employee.LoginNotUniqueException;
import com.jupiter.asclepi.core.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.employee.EmployeeRepository;
import com.jupiter.asclepi.core.service.EmployeeService;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
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

    @Override
    public Try<Employee> create(@Valid @NotNull CreateEmployeeRequest createRequest) {
        Employee toCreate = conversionService.convert(createRequest, Employee.class);
        return Try.of(() -> {
            if (employeeWithLoginExists(createRequest.getLogin())) {
                throw new LoginNotUniqueException(createRequest.getLogin());
            }
            return repository.save(Objects.requireNonNull(toCreate));
        });
    }

    @Override
    public Boolean delete(@NotNull Integer toDeleteId) {
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
            BeanUtils.copyProperties(toCopyFrom, existing);
            return repository.save(existing);
        });
    }

    @Override
    public Optional<Employee> getOne(@NotNull Integer employeeId) {
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
}
