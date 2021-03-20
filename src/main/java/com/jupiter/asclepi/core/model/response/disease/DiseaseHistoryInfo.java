package com.jupiter.asclepi.core.model.response.disease;

import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.Value;

import java.math.BigInteger;
import java.util.List;

@Value
public class DiseaseHistoryInfo {
    GetDiseaseHistoryRequest diseaseHistory;
    List<Integer> diagnosisIds;
    Integer doctorId;

    // todo created by and created when
}
