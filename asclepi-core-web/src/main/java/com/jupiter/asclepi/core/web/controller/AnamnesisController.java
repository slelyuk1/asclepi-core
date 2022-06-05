package com.jupiter.asclepi.core.web.controller;

import com.jupiter.asclepi.core.model.request.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import com.jupiter.asclepi.core.model.response.AnamnesisInfo;
import com.jupiter.asclepi.core.web.helper.api.CreateController;
import com.jupiter.asclepi.core.web.helper.api.delete.DeleteUsingPathVariableController;
import com.jupiter.asclepi.core.web.helper.api.get.GetUsingPathVariableController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public interface AnamnesisController extends
        GetUsingPathVariableController<BigInteger, AnamnesisInfo>,
        CreateController<CreateAnamnesisRequest, AnamnesisInfo>,
        DeleteUsingPathVariableController<BigInteger> {

    @GetMapping("/getForDiseaseHistory")
    List<AnamnesisInfo> getForDiseaseHistory(@NotNull @RequestBody GetDiseaseHistoryRequest request);
}
