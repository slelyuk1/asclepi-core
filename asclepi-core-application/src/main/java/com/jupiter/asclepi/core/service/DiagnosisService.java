package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.model.entity.disease.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.model.request.disease.diagnosis.GetDiagnosisRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DiagnosisService extends CrudService<GetDiagnosisRequest, CreateDiagnosisRequest, EditDiagnosisRequest, Diagnosis, Boolean> {

    List<Diagnosis> getForDiseaseHistory(@NotNull DiseaseHistory diseaseHistory);
}
