package com.jupiter.asclepi.core.model.response.people;

import com.jupiter.asclepi.core.model.entity.people.Role;
import lombok.Value;

@Value
public class EmployeeInfo {
    Integer id;
    String login;
    Role role;
    String name;
    String surname;
    String middleName;
    String additionalInfo;
}
