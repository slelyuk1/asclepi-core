package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.repository.entity.consultation.Consultation;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.service.helper.api.CrudService;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ConsultationService
        extends CrudService<GetConsultationRequest, CreateConsultationRequest, EditConsultationRequest, Consultation, Boolean> {

    List<Consultation> getForVisit(@NotNull Visit visit);

    List<Consultation> getForDiseaseHistory(@NotNull DiseaseHistory history);
}
