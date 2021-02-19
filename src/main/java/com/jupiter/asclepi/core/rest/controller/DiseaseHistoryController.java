package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.helper.api.business.service.CreateService;
import com.jupiter.asclepi.core.helper.api.business.service.EditService;
import com.jupiter.asclepi.core.helper.api.business.service.GetService;

import java.util.List;

public interface DiseaseHistoryController extends
        GetService<GetDiseaseHistoryRequest, DiseaseHistoryInfo>,
        CreateService<CreateDiseaseHistoryRequest, DiseaseHistoryInfo>,
        EditService<EditDiseaseHistoryRequest, DiseaseHistoryInfo> {

    List<DiseaseHistoryInfo> getForClient(Integer clientId);
    // todo close and abort disease history
}
