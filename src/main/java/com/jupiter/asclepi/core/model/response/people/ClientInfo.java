package com.jupiter.asclepi.core.model.response.people;

import com.jupiter.asclepi.core.model.entity.people.Job;
import lombok.Value;

@Value
public class ClientInfo {
    Integer id;
    String name;
    String surname;
    String middleName;
    String residence;
    // can be more than two ??? hehehe
    Boolean gender;
    Job job;
}
