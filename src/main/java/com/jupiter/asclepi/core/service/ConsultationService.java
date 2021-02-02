package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.entity.disease.Consultation;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;

import java.math.BigInteger;

public interface ConsultationService extends Crud<BigInteger, CreateConsultationRequest, EditConsultationRequest, Consultation> {
}
