package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.response.disease.AnalysisInfo;

import java.math.BigInteger;

public interface AnalysisService extends CrudService<BigInteger, CreateAnalysisRequest, EditAnalysisRequest, AnalysisInfo, Void> {
}
