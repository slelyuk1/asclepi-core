package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.consultation.CreateConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.EditConsultationRequest;
import com.jupiter.asclepi.core.model.request.consultation.GetConsultationRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.ConsultationInfo;
import com.jupiter.asclepi.core.web.helper.api.crud.CrudUsingRequestBodyController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ConsultationController
        extends CrudUsingRequestBodyController<GetConsultationRequest, CreateConsultationRequest, EditConsultationRequest, ConsultationInfo> {

    @PostMapping("/edit")
    @Override
    ResponseEntity<?> edit(@NotNull @RequestBody EditConsultationRequest editRequest);

    @GetMapping("/all")
    @Override
    List<ConsultationInfo> getAll();

    @GetMapping("/getForVisit")
    List<ConsultationInfo> getForVisit(@NotNull @RequestBody GetVisitRequest request);

    @GetMapping("/getForDiseaseHistory")
    List<ConsultationInfo> getForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest request);
}
