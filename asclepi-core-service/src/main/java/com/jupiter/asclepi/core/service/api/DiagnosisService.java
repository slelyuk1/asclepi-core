package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.diagnosis.GetDiagnosisRequest;
import com.jupiter.asclepi.core.repository.entity.diagnosis.Diagnosis;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DiagnosisService extends CrudService<GetDiagnosisRequest, CreateDiagnosisRequest, EditDiagnosisRequest, Diagnosis, Boolean> {

    List<Diagnosis> getForDiseaseHistory(@NotNull DiseaseHistory diseaseHistory);
}
