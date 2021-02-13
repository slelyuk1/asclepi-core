package com.jupiter.asclepi.core.service;

import com.jupiter.asclepi.core.helper.api.business.shared.Creation;
import com.jupiter.asclepi.core.helper.api.business.shared.Deletion;
import com.jupiter.asclepi.core.helper.api.business.shared.Getting;
import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;

import java.math.BigInteger;

public interface AnamnesisService extends Getting<BigInteger, Anamnesis>, Creation<CreateAnamnesisRequest, Anamnesis>, Deletion<BigInteger, Void> {
}
