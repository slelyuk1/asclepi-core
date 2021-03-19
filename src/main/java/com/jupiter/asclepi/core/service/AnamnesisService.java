package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CreateService;
import com.jupiter.asclepi.core.helper.api.business.service.DeleteService;
import com.jupiter.asclepi.core.helper.api.business.service.GetService;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;

import java.math.BigInteger;
import java.util.List;

public interface AnamnesisService extends
        GetService<BigInteger, Anamnesis>,
        CreateService<CreateAnamnesisRequest, Anamnesis>,
        DeleteService<BigInteger, Void> {

    List<Anamnesis> getForDiseaseHistory(DiseaseHistory history);
}
