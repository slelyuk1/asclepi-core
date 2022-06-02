package com.jupiter.asclepi.core.service.api;

import com.jupiter.asclepi.core.repository.entity.Anamnesis;
import com.jupiter.asclepi.core.repository.entity.DiseaseHistory;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.service.helper.api.CreateService;
import com.jupiter.asclepi.core.service.helper.api.DeleteService;
import com.jupiter.asclepi.core.service.helper.api.GetService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface AnamnesisService extends
        GetService<BigInteger, Anamnesis>,
        CreateService<CreateAnamnesisRequest, Anamnesis>,
        DeleteService<BigInteger, Boolean> {

    List<Anamnesis> getForDiseaseHistory(@Valid @NotNull DiseaseHistory history);
}
