package com.jupiter.asclepi.core.model.request.diagnosis;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class CreateDiagnosisRequest implements Cloneable {
    private GetDiseaseHistoryRequest diseaseHistory;
    private String disease;
    private Boolean isFinal;
    private String complications;
    private String etiologyAndPathogenesis;
    private String specialityOfCourse;

    @SneakyThrows
    @Override
    public CreateDiagnosisRequest clone() {
        CreateDiagnosisRequest cloned = (CreateDiagnosisRequest) super.clone();
        cloned.diseaseHistory = diseaseHistory.clone();
        return cloned;
    }
}
