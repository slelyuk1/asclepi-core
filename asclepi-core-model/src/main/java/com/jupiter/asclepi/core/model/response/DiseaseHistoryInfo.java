package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;

import java.util.List;

public record DiseaseHistoryInfo(
        GetDiseaseHistoryRequest diseaseHistory,
        List<Integer> diagnosisIds,
        Integer doctorId
        // todo created by and created when
) {
}
