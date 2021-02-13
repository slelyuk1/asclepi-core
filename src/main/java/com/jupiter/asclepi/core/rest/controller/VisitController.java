package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.ControllerCrud;
import com.jupiter.asclepi.core.model.request.disease.visit.CreateVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.EditVisitRequest;
import com.jupiter.asclepi.core.model.request.disease.visit.GetVisitRequest;
import com.jupiter.asclepi.core.model.response.disease.VisitInfo;

import java.math.BigInteger;
import java.util.List;

public interface VisitController extends ControllerCrud<GetVisitRequest, CreateVisitRequest, EditVisitRequest, VisitInfo> {

    List<VisitInfo> getByDiseaseHistory(BigInteger diseaseHistoryId);
    // todo finish visit
}
