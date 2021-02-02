package com.jupiter.asclepi.core.model.entity.people;

import com.jupiter.asclepi.core.helper.object.api.AbstractCreationAware;
import lombok.Data;

@Data
public class Employee extends AbstractCreationAware<Employee> {
    private Integer id;
    private String login;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private String middleName;
    private String additionalInfo;
}