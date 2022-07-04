package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.helper.api.DeleteService;
import com.jupiter.asclepi.core.service.helper.api.GetService;
import com.jupiter.asclepi.core.service.helper.api.v2.CreateService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface AnamnesisService extends
        GetService<BigInteger, Anamnesis>,
        CreateService<CreateAnamnesisRequest, Anamnesis, BigInteger>,
        DeleteService<BigInteger, Boolean> {

    @Override
    default Class<Anamnesis> getEntityClass() {
        return Anamnesis.class;
    }

    List<Anamnesis> getForDiseaseHistory(@Valid @NotNull DiseaseHistory history);
}
