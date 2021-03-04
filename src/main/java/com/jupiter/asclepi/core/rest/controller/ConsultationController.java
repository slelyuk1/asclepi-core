package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.response.disease.ConsultationInfo;

import java.math.BigInteger;

public interface ConsultationController
        extends CrudController<BigInteger, CreateConsultationRequest, EditConsultationRequest, ConsultationInfo> {
}
