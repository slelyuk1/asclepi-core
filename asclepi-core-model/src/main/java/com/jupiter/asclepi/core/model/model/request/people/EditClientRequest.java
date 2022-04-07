package com.jupiter.asclepi.core.model.model.request.people;

import com.jupiter.asclepi.core.model.model.other.Job;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class EditClientRequest {
    @NotNull
    private BigInteger id;
    private String name;
    private String surname;
    private String middleName;
    private String residence;
    private Boolean gender;
    private Job job;
}
