package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.api.business.shared.Creation;
import com.jupiter.asclepi.core.helper.api.business.shared.Deletion;
import com.jupiter.asclepi.core.helper.api.business.shared.Getting;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.response.disease.AnamnesisInfo;

import java.math.BigInteger;

public interface AnamnesisController extends
        Getting<BigInteger, AnamnesisInfo>,
        Creation<CreateAnamnesisRequest, AnamnesisInfo>,
        Deletion<BigInteger, Void> {
}
