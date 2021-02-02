package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.entity.disease.Diagnosis;
import com.jupiter.asclepi.core.model.entity.disease.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.EditDiagnosisRequest;

import java.math.BigInteger;
import java.util.List;

public interface DiagnosisService extends Crud<BigInteger, CreateDiagnosisRequest, EditDiagnosisRequest, Diagnosis> {

    List<Diagnosis> getForDiseaseHistory(DiseaseHistory diseaseHistory);
}
