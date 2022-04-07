package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CreateService;
import com.jupiter.asclepi.core.helper.api.business.service.EditService;
import com.jupiter.asclepi.core.helper.api.business.service.GetAllService;
import com.jupiter.asclepi.core.helper.api.business.service.GetService;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;

import java.util.List;

public interface VisitService extends
        CreateService<CreateVisitRequest, Visit>,
        EditService<EditVisitRequest, Visit>,
        GetService<GetVisitRequest, Visit>,
        GetAllService<Visit> {

    List<Visit> getForDiseaseHistory(DiseaseHistory diseaseHistory);
    // todo finish visit
}
