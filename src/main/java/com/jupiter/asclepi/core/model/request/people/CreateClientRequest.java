package com.jupiter.asclepi.core.model.request.people;

import com.jupiter.asclepi.core.model.other.Job;
import lombok.Data;

@Data
public class CreateClientRequest {
    private String name;
    private String surname;
    private String middleName;
    private String residence;
    private Boolean gender;
    // todo maybe other class
    private Job job;
}
