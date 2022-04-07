package com.jupiter.asclepi.core.model.response.people;

import com.jupiter.asclepi.core.model.other.Job;
import lombok.Value;

import java.math.BigInteger;

@Value
public class ClientInfo {
    BigInteger id;
    String name;
    String surname;
    String middleName;
    String residence;
    Boolean gender;
    Job job;
}
