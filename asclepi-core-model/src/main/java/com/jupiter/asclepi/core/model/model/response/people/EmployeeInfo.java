package com.jupiter.asclepi.core.model.model.response.people;

import com.jupiter.asclepi.core.model.model.other.Role;
import lombok.Value;

@Value
public class EmployeeInfo {
    int id;
    String login;
    Role role;
    String name;
    String surname;
    String middleName;
    String additionalInfo;
}
