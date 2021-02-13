package com.jupiter.asclepi.core.service.impl.employee;

import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.EmployeeRepository;
import com.jupiter.asclepi.core.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final ConversionService conversionService;
    private final EmployeeRepository repository;

    @Override
    public Employee create(CreateEmployeeRequest createRequest) {
        Employee toCreate = conversionService.convert(createRequest, Employee.class);
        return repository.save(Objects.requireNonNull(toCreate));
    }

    @Override
    public Boolean delete(Integer toDeleteId) {
        return repository.findById(toDeleteId)
                .map(toDelete -> {
                    repository.delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    // todo correct implementation
    @Override
    public Optional<Employee> edit(EditEmployeeRequest editRequest) {
        return Optional.ofNullable(conversionService.convert(editRequest, Employee.class))
                .filter(toSave -> Objects.nonNull(toSave.getId()))
                .filter(toSave -> repository.existsById(toSave.getId()))
                .map(repository::save);
    }

    @Override
    public Optional<Employee> getOne(Integer getRequest) {
        return repository.findById(getRequest);
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }
}
