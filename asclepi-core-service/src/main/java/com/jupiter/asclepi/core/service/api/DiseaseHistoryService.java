package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.Client;
import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.service.helper.api.CreateService;
import com.jupiter.asclepi.core.service.helper.api.EditService;
import com.jupiter.asclepi.core.service.helper.api.GetAllService;
import com.jupiter.asclepi.core.service.helper.api.GetService;

import java.util.List;

public interface DiseaseHistoryService extends
        CreateService<CreateDiseaseHistoryRequest, DiseaseHistory>,
        EditService<EditDiseaseHistoryRequest, DiseaseHistory>,
        GetService<GetDiseaseHistoryRequest, DiseaseHistory>,
        GetAllService<DiseaseHistory> {
    List<DiseaseHistory> getForClient(Client client);
}
