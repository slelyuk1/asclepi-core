package com.jupiter.asclepi.core.model.request.employee;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditEmployeeRequest {
    @NotNull
    private Integer id;
    private String login;
    private String password;
    private Integer roleId;
    private String name;
    private String surname;
    private String middleName;
    private String additionalInfo;
}
