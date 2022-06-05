package com.jupiter.asclepi.core.model.request.employee;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateEmployeeRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotNull
    private Integer roleId;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private String middleName;
    private String additionalInfo;

}
