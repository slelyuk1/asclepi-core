package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import lombok.Value;

import java.util.List;

@Value
public class DiseaseHistoryInfo {
    GetDiseaseHistoryRequest diseaseHistory;
    List<Integer> diagnosisIds;
    Integer doctorId;

    // todo created by and created when
}
