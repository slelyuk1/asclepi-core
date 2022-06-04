package com.jupiter.asclepi.core.model.response;

import lombok.Value;

@Value
public class EmployeeInfo {
    int id;
    String login;
    Integer roleId;
    String name;
    String surname;
    String middleName;
    String additionalInfo;
}
