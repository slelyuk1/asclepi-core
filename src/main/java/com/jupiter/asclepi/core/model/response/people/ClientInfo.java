package com.jupiter.asclepi.core.model.response.people;

import com.jupiter.asclepi.core.model.other.Job;
import lombok.Value;

@Value
public class ClientInfo {
    Integer id;
    String name;
    String surname;
    String middleName;
    String residence;
    Boolean gender;
    Job job;
}
