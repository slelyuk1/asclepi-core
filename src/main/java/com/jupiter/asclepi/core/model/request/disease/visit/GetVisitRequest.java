package com.jupiter.asclepi.core.model.request.disease.visit;

import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.Data;

import java.math.BigInteger;

@Data
public class GetVisitRequest {
    private GetDiseaseHistoryRequest diseaseHistory;
    private Integer number;
}
