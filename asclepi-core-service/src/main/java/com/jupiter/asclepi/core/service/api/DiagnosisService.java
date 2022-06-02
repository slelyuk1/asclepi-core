package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.repository.entity.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DiagnosisService extends CrudService<GetDiagnosisRequest, CreateDiagnosisRequest, EditDiagnosisRequest, Diagnosis, Boolean> {

    List<Diagnosis> getForDiseaseHistory(@NotNull DiseaseHistory diseaseHistory);
}
