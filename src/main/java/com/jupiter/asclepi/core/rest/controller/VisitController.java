package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CreateController;
import com.jupiter.asclepi.core.helper.api.business.controller.EditController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetAllController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetController;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.response.disease.VisitInfo;

import java.math.BigInteger;
import java.util.List;

public interface VisitController extends
        GetController<BigInteger, VisitInfo>,
        GetAllController<VisitInfo>,
        CreateController<CreateVisitRequest>,
        EditController<EditVisitRequest> {

    List<VisitInfo> getByDiseaseHistory(BigInteger diseaseHistoryId);
    // todo implement
    // ResponseEntity<?> finish(BigInteger diseaseHistoryId);
}
