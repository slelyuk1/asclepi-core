package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.service.api.Crud;
import com.jupiter.asclepi.core.model.entity.disease.DiseaseHistory;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.request.disease.history.CreateDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.EditDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.request.disease.history.GetDiseaseHistoryRequest;

import java.util.List;

public interface DiseaseHistoryService extends Crud<GetDiseaseHistoryRequest, CreateDiseaseHistoryRequest, EditDiseaseHistoryRequest, DiseaseHistory> {
    List<DiseaseHistory> getForClient(Client client);
}
