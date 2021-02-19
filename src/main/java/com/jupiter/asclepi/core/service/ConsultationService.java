package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.entity.disease.Consultation;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;

import java.math.BigInteger;

public interface ConsultationService extends CrudService<BigInteger, CreateConsultationRequest, EditConsultationRequest, Consultation, Void> {
}
