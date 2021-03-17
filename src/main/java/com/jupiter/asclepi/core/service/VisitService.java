package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CrudService;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;

import java.util.List;

public interface VisitService extends CrudService<GetVisitRequest, CreateVisitRequest, EditVisitRequest, Visit, Void> {

    List<Visit> getByDiseaseHistory(DiseaseHistory diseaseHistory);
    // todo finish visit
}
