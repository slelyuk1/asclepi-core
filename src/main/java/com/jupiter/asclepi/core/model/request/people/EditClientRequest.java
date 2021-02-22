package com.jupiter.asclepi.core.model.request.people;

import com.jupiter.asclepi.core.model.other.Job;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditClientRequest {
    @NotNull
    private Integer id;
    private String name;
    private String surname;
    private String middleName;
    private String residence;
    private Boolean gender;
    private Job job;
}
