package com.jupiter.asclepi.core.model.response.people;

import lombok.Value;

@Value
public class EmployeeInfo {
    int id;
    String login;
    int roleId;
    String name;
    String surname;
    String middleName;
    String additionalInfo;
}
