package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.ControllerCrud;
import com.jupiter.asclepi.core.model.response.disease.ConsultationInfo;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;

import java.math.BigInteger;

public interface ConsultationController
        extends ControllerCrud<BigInteger, CreateConsultationRequest, EditConsultationRequest, ConsultationInfo> {
}
