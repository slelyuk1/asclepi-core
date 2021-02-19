package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.service.CreateService;
import com.jupiter.asclepi.core.helper.api.business.service.DeleteService;
import com.jupiter.asclepi.core.helper.api.business.service.GetService;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;

import java.math.BigInteger;

public interface AnamnesisService extends
        GetService<BigInteger, Anamnesis>,
        CreateService<CreateAnamnesisRequest, Anamnesis>,
        DeleteService<BigInteger, Void> {
}
