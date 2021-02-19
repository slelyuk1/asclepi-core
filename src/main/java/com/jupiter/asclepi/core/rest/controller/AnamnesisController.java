package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.controller.CreateController;
import com.jupiter.asclepi.core.helper.api.business.controller.DeleteController;
import com.jupiter.asclepi.core.helper.api.business.controller.GetController;
import com.jupiter.asclepi.core.helper.api.business.service.CreateService;
import com.jupiter.asclepi.core.helper.api.business.service.DeleteService;
import com.jupiter.asclepi.core.helper.api.business.service.GetService;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.response.disease.AnamnesisInfo;

import java.math.BigInteger;

public interface AnamnesisController extends
        GetController<BigInteger, AnamnesisInfo>,
        CreateController<CreateAnamnesisRequest>,
        DeleteController<BigInteger> {
}
