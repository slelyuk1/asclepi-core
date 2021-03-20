package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CrudController;
import com.jupiter.asclepi.core.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.disease.ConsultationInfo;

import java.util.List;

public interface ConsultationController
        extends CrudController<GetConsultationRequest, CreateConsultationRequest, EditConsultationRequest, ConsultationInfo> {

    List<ConsultationInfo> getForVisit(GetVisitRequest request);

    List<ConsultationInfo> getForDiseaseHistory(GetDiseaseHistoryRequest request);
}
