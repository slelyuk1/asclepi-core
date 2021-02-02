package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.DiseaseHistoryInfo;
import com.jupiter.asclepi.core.helper.service.api.Creation;
import com.jupiter.asclepi.core.helper.service.api.Editing;
import com.jupiter.asclepi.core.helper.service.api.Getting;

import java.util.List;

public interface DiseaseHistoryController extends
        Getting<GetDiseaseHistoryRequest, DiseaseHistoryInfo>,
        Creation<CreateDiseaseHistoryRequest, DiseaseHistoryInfo>,
        Editing<EditDiseaseHistoryRequest, DiseaseHistoryInfo> {

    List<DiseaseHistoryInfo> getForClient(Integer clientId);
    // todo close and abort disease history
}
