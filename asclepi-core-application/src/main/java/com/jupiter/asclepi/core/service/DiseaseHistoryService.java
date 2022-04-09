package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CreateService;
import com.jupiter.asclepi.core.helper.api.business.service.EditService;
import com.jupiter.asclepi.core.helper.api.business.service.GetAllService;
import com.jupiter.asclepi.core.helper.api.business.service.GetService;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.people.Client;
import com.jupiter.asclepi.core.model.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.model.request.disease.history.GetDiseaseHistoryRequest;

import java.util.List;

public interface DiseaseHistoryService extends
        CreateService<CreateDiseaseHistoryRequest, DiseaseHistory>,
        EditService<EditDiseaseHistoryRequest, DiseaseHistory>,
        GetService<GetDiseaseHistoryRequest, DiseaseHistory>,
        GetAllService<DiseaseHistory> {
    List<DiseaseHistory> getForClient(Client client);
}
