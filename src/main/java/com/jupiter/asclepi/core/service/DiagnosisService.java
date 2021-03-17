package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.entity.disease.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.EditDiagnosisRequest;

import java.math.BigInteger;
import java.util.List;

public interface DiagnosisService extends CrudService<BigInteger, CreateDiagnosisRequest, EditDiagnosisRequest, Diagnosis, Void> {

    List<Diagnosis> getForDiseaseHistory(DiseaseHistory diseaseHistory);
}
