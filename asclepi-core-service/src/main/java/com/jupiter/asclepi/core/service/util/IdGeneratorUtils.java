package com.jupiter.asclepi.core.service.util;

import java.math.BigInteger;

// todo make normal
public class IdGeneratorUtils {

    private static BigInteger generatedId = BigInteger.ONE;

    public static BigInteger generateId() {
        BigInteger generated = generatedId;
        generatedId = generatedId.add(BigInteger.ONE);
        return generated;
    }

}
