package com.jupiter.asclepi.core.rest.controller;

import com.jupiter.asclepi.core.helper.service.api.Creation;
import com.jupiter.asclepi.core.helper.service.api.Deletion;
import com.jupiter.asclepi.core.helper.service.api.Getting;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import com.jupiter.asclepi.core.model.response.disease.AnamnesisInfo;

import java.math.BigInteger;

public interface AnamnesisController extends
        Getting<BigInteger, AnamnesisInfo>,
        Creation<CreateAnamnesisRequest, AnamnesisInfo>,
        Deletion<BigInteger> {
}
