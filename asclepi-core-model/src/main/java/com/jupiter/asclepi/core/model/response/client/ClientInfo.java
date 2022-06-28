package com.jupiter.asclepi.core.model.response.client;

import com.jupiter.asclepi.core.model.response.common.CreationInfo;

import java.math.BigInteger;

public record ClientInfo(
        BigInteger id,
        String name,
        String surname,
        String middleName,
        String residence,
        Boolean gender,
        JobInfo job,
        CreationInfo creation
) {
}
