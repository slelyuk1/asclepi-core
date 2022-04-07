package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.model.entity.disease.consultation.Consultation;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.model.request.disease.consultation.GetConsultationRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ConsultationService
        extends CrudService<GetConsultationRequest, CreateConsultationRequest, EditConsultationRequest, Consultation, Boolean> {

    List<Consultation> getForVisit(@NotNull Visit visit);

    List<Consultation> getForDiseaseHistory(@NotNull DiseaseHistory history);
}
