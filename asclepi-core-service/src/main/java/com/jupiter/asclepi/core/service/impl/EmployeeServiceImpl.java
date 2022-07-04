package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.employee.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.service.exception.employee.LoginNotUniqueException;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.helper.api.v2.AbstractService;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
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
public class EmployeeServiceImpl extends AbstractService<Employee, Integer> implements EmployeeService {

    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(ConversionService conversionService,
                               JpaRepository<Employee, Integer> repository,
                               PasswordEncoder passwordEncoder) {
        super(conversionService, repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void preProcessBeforeCreation(CreateEmployeeRequest request, Employee toCreate) {
        String newLogin = toCreate.getLogin();
        if (employeeWithLoginExists(newLogin)) {
            throw new LoginNotUniqueException(newLogin);
        }
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
    }

    @Override
    public List<Employee> postProcessAfterGetAll(List<Employee> allFound) {
        return allFound.stream()
                .filter(employee -> Role.ADMIN != employee.getRole())
                .toList();
    }

    @Override
    public Boolean delete(@Valid @NotNull Integer toDeleteId) {
        return getRepository().findById(toDeleteId)
                .map(toDelete -> {
                    getRepository().delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Employee edit(@Valid @NotNull EditEmployeeRequest editRequest) {
        Employee existing = getRepository().findById(editRequest.getId())
                .orElseThrow(() -> new NonExistentIdException("Employee", editRequest.getId()));
        Employee toCopyFrom = Objects.requireNonNull(getConversionService().convert(editRequest, Employee.class));
        String newLogin = toCopyFrom.getLogin();
        if (!existing.getLogin().equals(newLogin) && employeeWithLoginExists(newLogin)) {
            throw new LoginNotUniqueException(newLogin);
        }
        CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, existing);
        return getRepository().save(existing);
    }

    @Override
    public Optional<Employee> getOne(@Valid @NotNull Integer employeeId) {
        return getRepository().findById(employeeId);
    }

    @Override
    public Optional<Employee> findEmployeeByLogin(@NotNull String login) {
        Employee toSearch = new Employee();
        toSearch.setLogin(login);
        return getRepository().findOne(Example.of(toSearch));
    }

    private boolean employeeWithLoginExists(String login) {
        Employee employeeWithLogin = new Employee();
        employeeWithLogin.setLogin(login);
        return getRepository().exists(Example.of(employeeWithLogin));
    }
}
