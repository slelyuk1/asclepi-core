package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.response.disease.AnalysisInfo;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.EditAnalysisRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateAnalysisRequest;

import java.math.BigInteger;

public interface AnalysisController extends Crud<BigInteger, CreateAnalysisRequest, EditAnalysisRequest, AnalysisInfo> {
}
