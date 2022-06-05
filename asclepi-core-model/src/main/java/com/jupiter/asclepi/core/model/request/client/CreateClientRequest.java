package com.jupiter.asclepi.core.model.request.client;

import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateClientRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    private String middleName;
    @NotEmpty
    private String residence;
    @NotNull
    private Boolean gender;
    @NotNull
    private JobRequest job;

}
