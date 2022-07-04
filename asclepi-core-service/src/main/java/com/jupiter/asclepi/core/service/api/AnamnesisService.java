package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.model.request.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.service.helper.api.v2.CreateService;
import com.jupiter.asclepi.core.service.helper.api.v2.DeleteService;
import com.jupiter.asclepi.core.service.helper.api.v2.GetService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface AnamnesisService extends
        GetService<BigInteger, Anamnesis, BigInteger>,
        CreateService<CreateAnamnesisRequest, Anamnesis, BigInteger>,
        DeleteService<BigInteger, Anamnesis, BigInteger> {

    @Override
    default Class<Anamnesis> getEntityClass() {
        return Anamnesis.class;
    }

    @Override
    default Class<BigInteger> getIdClass(){
        return BigInteger.class;
    }

    List<Anamnesis> getForDiseaseHistory(@Valid @NotNull DiseaseHistory history);
}
