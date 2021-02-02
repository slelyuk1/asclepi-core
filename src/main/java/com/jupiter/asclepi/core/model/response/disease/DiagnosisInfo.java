package com.jupiter.asclepi.core.model.response.disease;

import lombok.Data;
import lombok.Value;

import java.math.BigInteger;

@Value
public class DiagnosisInfo {
    BigInteger id;
    BigInteger diseaseHistoryId;
    String disease;
    String complications;
    String concomitantPathology;
}
