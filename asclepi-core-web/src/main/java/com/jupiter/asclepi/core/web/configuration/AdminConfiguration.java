package com.jupiter.asclepi.core.web.configuration;

import com.jupiter.asclepi.core.model.model.other.Role;
import com.jupiter.asclepi.core.model.model.request.people.CreateEmployeeRequest;
import com.jupiter.asclepi.core.service.EmployeeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfiguration implements InitializingBean {
    private static final String ADDITIONAL_INFO = "Main admin user. Do not remove!";
    private final String adminLogin;
    private final String adminPassword;
    private final String adminName;
    private final String adminSurname;
    private final EmployeeService employeeService;

    @Autowired
    public AdminConfiguration(@Value("${security.admin.login}") String adminLogin,
                              @Value("${security.admin.password}") String adminPassword,
                              @Value("${security.admin.name}") String adminName,
                              @Value("${security.admin.surname}") String adminSurname,
                              EmployeeService employeeService) {
        this.adminLogin = adminLogin;
        this.adminPassword = adminPassword;
        this.adminName = adminName;
        this.adminSurname = adminSurname;
        this.employeeService = employeeService;
    }


    @Override
    public void afterPropertiesSet() {
        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest();
        createEmployeeRequest.setLogin(adminLogin);
        createEmployeeRequest.setPassword(adminPassword);
        createEmployeeRequest.setRole(Role.ADMIN);
        createEmployeeRequest.setName(adminName);
        createEmployeeRequest.setSurname(adminSurname);
        createEmployeeRequest.setAdditionalInfo(ADDITIONAL_INFO);
        employeeService.create(createEmployeeRequest);
    }
}
