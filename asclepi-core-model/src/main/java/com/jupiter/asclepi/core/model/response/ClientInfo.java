package com.jupiter.asclepi.core.model.response;

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
    JobInfo job;
}
