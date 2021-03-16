package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CreateController;
import com.jupiter.asclepi.core.helper.api.business.controller.EditController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetAllController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetController;
import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.disease.DiseaseHistoryInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DiseaseHistoryController extends
        GetController<GetDiseaseHistoryRequest, DiseaseHistoryInfo>,
        GetAllController<DiseaseHistoryInfo>,
        CreateController<CreateDiseaseHistoryRequest>,
        EditController<EditDiseaseHistoryRequest> {

    List<DiseaseHistoryInfo> getForClient(Integer clientId);

    ResponseEntity<?> abort(GetDiseaseHistoryRequest request);

    ResponseEntity<?> close(GetDiseaseHistoryRequest request);
}
