package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.ConsultationInfo;
import com.jupiter.asclepi.core.repository.entity.consultation.Consultation;
import com.jupiter.asclepi.core.repository.entity.consultation.ConsultationId;
import com.jupiter.asclepi.core.web.helper.api.CrudController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ConsultationController
        extends CrudController<Consultation, ConsultationId, GetConsultationRequest, CreateConsultationRequest, EditConsultationRequest, ConsultationInfo> {

    @Override
    default Class<ConsultationInfo> getResponseClass() {
        return ConsultationInfo.class;
    }

    @Override
    default ConsultationInfo create(@RequestBody CreateConsultationRequest createRequest) {
        return CrudController.super.create(createRequest);
    }

    @Override
    @DeleteMapping
    default void delete(@RequestBody GetConsultationRequest deleteRequest) {
        CrudController.super.delete(deleteRequest);
    }

    @Override
    default List<ConsultationInfo> getAll() {
        return CrudController.super.getAll();
    }

    @Override
    @GetMapping
    default ConsultationInfo getOne(@RequestBody GetConsultationRequest getRequest) {
        return CrudController.super.getOne(getRequest);
    }

    @Override
    default ConsultationInfo edit(@RequestBody EditConsultationRequest editRequest) {
        return CrudController.super.edit(editRequest);
    }

    @GetMapping("/getForVisit")
    List<ConsultationInfo> getForVisit(@NotNull @RequestBody GetVisitRequest request);

    @GetMapping("/getForDiseaseHistory")
    List<ConsultationInfo> getForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest request);
}
