package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.visit.GetVisitRequest;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.entity.visit.VisitId;
import com.jupiter.asclepi.core.service.helper.api.v2.CreateService;
import com.jupiter.asclepi.core.service.helper.api.v2.EditService;
import com.jupiter.asclepi.core.service.helper.api.v2.GetAllService;
import com.jupiter.asclepi.core.service.helper.api.v2.GetService;

import java.util.List;

public interface VisitService extends
        CreateService<CreateVisitRequest, Visit, VisitId>,
        EditService<EditVisitRequest, Visit, VisitId>,
        GetService<GetVisitRequest, Visit, VisitId>,
        GetAllService<Visit, VisitId> {

    @Override
    default Class<Visit> getEntityClass() {
        return Visit.class;
    }

    @Override
    default Class<VisitId> getIdClass() {
        return VisitId.class;
    }

    List<Visit> getForDiseaseHistory(DiseaseHistory diseaseHistory);

    // todo finish visit
}
