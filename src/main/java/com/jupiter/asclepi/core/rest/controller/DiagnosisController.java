package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.CreateDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.diagnosis.EditDiagnosisRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.DiagnosisInfo;

import java.math.BigInteger;
import java.util.List;

public interface DiagnosisController extends CrudController<BigInteger, CreateDiagnosisRequest, EditDiagnosisRequest, DiagnosisInfo> {

    List<DiagnosisInfo> getForDiseaseHistory(GetDiseaseHistoryRequest request);
}
