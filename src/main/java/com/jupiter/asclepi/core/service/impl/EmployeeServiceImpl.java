package com.jupiter.asclepi.core.service.impl;

import com.jupiter.asclepi.core.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.model.request.people.EditEmployeeRequest;
import com.jupiter.asclepi.core.repository.EmployeeRepository;
import com.jupiter.asclepi.core.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final String ROLE_NOT_EXISTS_MESSAGE = "Role with id %d doesn't exist!";

    private final EmployeeRepository repository;

    @Override
    public Employee create(CreateEmployeeRequest createRequest) {
        Employee toCreate = new Employee();
        toCreate.setLogin(createRequest.getLogin());
        toCreate.setPassword(createRequest.getPassword());
        toCreate.setName(createRequest.getName());
        toCreate.setSurname(createRequest.getSurname());
        toCreate.setMiddleName(createRequest.getMiddleName());
        toCreate.setRole(createRequest.getRole());
        toCreate.setAdditionalInfo(createRequest.getAdditionalInfo());
        return repository.save(toCreate);
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

    @Override
    public Employee edit(EditEmployeeRequest editRequest) {
        throw new UnsupportedOperationException("This operation is not implemented yet");
    }

    @Override
    public Optional<Employee> getOne(Integer getRequest) {
        throw new UnsupportedOperationException("This operation is not implemented yet");
    }

    @Override
    public List<Employee> getAll() {
        throw new UnsupportedOperationException("This operation is not implemented yet");
    }
}
