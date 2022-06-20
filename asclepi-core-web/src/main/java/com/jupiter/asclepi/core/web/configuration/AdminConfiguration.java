package com.jupiter.asclepi.core.web.configuration;

import com.jupiter.asclepi.core.model.request.employee.CreateEmployeeRequest;
import com.jupiter.asclepi.core.repository.entity.employee.Role;
import com.jupiter.asclepi.core.service.api.EmployeeService;
import com.jupiter.asclepi.core.web.configuration.property.AdminProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfiguration {

    private static final String ADDITIONAL_INFO = "Main admin user. Do not remove!";

    @Bean
    public CommandLineRunner adminCreationRunner(EmployeeService employeeService, AdminProperties properties) {
        return args -> createAdminInSystem(employeeService, properties);
    }

    private void createAdminInSystem(EmployeeService employeeService, AdminProperties properties) {
        final String adminLogin = properties.login();
        if (employeeService.findEmployeeByLogin(adminLogin).isPresent()) {
            return;
        }
        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest();
        createEmployeeRequest.setLogin(properties.login());
        createEmployeeRequest.setPassword(properties.password());
        createEmployeeRequest.setRoleId(Role.ADMIN.getId());
        createEmployeeRequest.setName(properties.name());
        createEmployeeRequest.setSurname(properties.surname());
        createEmployeeRequest.setAdditionalInfo(ADDITIONAL_INFO);
        employeeService.create(createEmployeeRequest);
    }

}
