package com.jupiter.asclepi.core.model.model.request.disease.diagnosis;

import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDiagnosisRequest {
    private GetDiseaseHistoryRequest diseaseHistory;
    private Integer number;
}
