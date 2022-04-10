package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.service.helper.api.CreateService;
import com.jupiter.asclepi.core.service.helper.api.EditService;
import com.jupiter.asclepi.core.service.helper.api.GetAllService;
import com.jupiter.asclepi.core.service.helper.api.GetService;

import java.util.List;

public interface VisitService extends
        CreateService<CreateVisitRequest, Visit>,
        EditService<EditVisitRequest, Visit>,
        GetService<GetVisitRequest, Visit>,
        GetAllService<Visit> {

    List<Visit> getForDiseaseHistory(DiseaseHistory diseaseHistory);
    // todo finish visit
}
