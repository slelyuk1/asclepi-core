package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.repository.entity.client.Client;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistoryId;
import com.jupiter.asclepi.core.service.helper.api.v2.CreateService;
import com.jupiter.asclepi.core.service.helper.api.v2.EditService;
import com.jupiter.asclepi.core.service.helper.api.v2.GetAllService;
import com.jupiter.asclepi.core.service.helper.api.v2.GetService;

import java.util.List;

public interface DiseaseHistoryService extends
        CreateService<CreateDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId>,
        EditService<EditDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId>,
        GetService<GetDiseaseHistoryRequest, DiseaseHistory, DiseaseHistoryId>,
        GetAllService<DiseaseHistory, DiseaseHistoryId> {

    @Override
    default Class<DiseaseHistory> getEntityClass() {
        return DiseaseHistory.class;
    }

    @Override
    default Class<DiseaseHistoryId> getIdClass() {
        return DiseaseHistoryId.class;
    }

    List<DiseaseHistory> getForClient(Client client);
}
