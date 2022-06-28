package com.jupiter.asclepi.core.model.response;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.common.CreationInfo;

import java.util.List;

public record DiseaseHistoryInfo(
        GetDiseaseHistoryRequest diseaseHistory,
        List<Integer> diagnosisIds,
        Integer doctorId,
        CreationInfo creation
) {
}
