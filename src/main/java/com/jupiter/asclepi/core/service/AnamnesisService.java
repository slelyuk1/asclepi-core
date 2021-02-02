package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.service.api.Creation;
import com.jupiter.asclepi.core.helper.service.api.Deletion;
import com.jupiter.asclepi.core.helper.service.api.Getting;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;

import java.math.BigInteger;

public interface AnamnesisService extends Getting<BigInteger, Anamnesis>, Creation<CreateAnamnesisRequest, Anamnesis>, Deletion<BigInteger> {
}
