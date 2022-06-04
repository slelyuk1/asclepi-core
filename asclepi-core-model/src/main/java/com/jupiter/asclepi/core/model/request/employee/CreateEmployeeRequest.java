package com.jupiter.asclepi.core.model.request.employee;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateEmployeeRequest implements Cloneable {
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

    @Override
    public CreateEmployeeRequest clone() {
        try {
            return (CreateEmployeeRequest) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("CloneNotSupportedException cannot be thrown here", e);
        }
    }
}
