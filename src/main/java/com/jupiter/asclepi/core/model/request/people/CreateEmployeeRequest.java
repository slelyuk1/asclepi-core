package com.jupiter.asclepi.core.model.request.people;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateEmployeeRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    // todo enum
    @NotNull
    private Integer roleId;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String middleName;
    private String additionalInfo;
}
