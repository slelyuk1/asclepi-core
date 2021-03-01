package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateAnalysisRequest;
import com.jupiter.asclepi.core.model.response.disease.AnalysisInfo;

import java.math.BigInteger;

public interface AnalysisController extends CrudController<BigInteger, CreateAnalysisRequest, EditAnalysisRequest, AnalysisInfo> {
}
