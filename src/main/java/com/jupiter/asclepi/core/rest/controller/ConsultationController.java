package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.response.disease.ConsultationInfo;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;

import java.math.BigInteger;

public interface ConsultationController
        extends Crud<BigInteger, CreateConsultationRequest, EditConsultationRequest, ConsultationInfo> {
}
