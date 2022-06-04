package com.jupiter.asclepi.core.model.request.people;

import com.jupiter.asclepi.core.model.response.JobInfo;
import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateClientRequest implements Cloneable {
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

    @SneakyThrows
    @Override
    public CreateClientRequest clone() {
        CreateClientRequest cloned = (CreateClientRequest) super.clone();
        cloned.job = job.clone();
        return cloned;
    }
}
