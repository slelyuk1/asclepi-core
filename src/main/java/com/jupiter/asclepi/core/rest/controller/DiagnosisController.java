package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.response.disease.DiagnosisInfo;

import java.math.BigInteger;
import java.util.List;

public interface DiagnosisController extends Crud<BigInteger, CreateDiagnosisRequest, EditDiagnosisRequest, DiagnosisInfo> {

    List<DiagnosisInfo> getForDiseaseHistory(BigInteger diseaseHistoryId);
}
