package com.jupiter.asclepi.core.model.response.client;

import java.math.BigInteger;

public record ClientInfo(
        BigInteger id,
        String name,
        String surname,
        String middleName,
        String residence,
        Boolean gender,
        JobInfo job
) {
}
