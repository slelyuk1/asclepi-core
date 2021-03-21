package com.jupiter.asclepi.core.service.impl.anamnesis.converter;

import com.jupiter.asclepi.core.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.request.disease.anamnesis.CreateAnamnesisRequest;
import org.springframework.core.convert.converter.Converter;

public class CreateAnamnesisRequestConverter implements Converter<CreateAnamnesisRequest, Anamnesis> {
    @Override
    public Anamnesis convert(CreateAnamnesisRequest source) {
        Anamnesis anamnesis = new Anamnesis();
        anamnesis.setComplaints(source.getComplaints());
        anamnesis.setVitae(source.getVitae());
        anamnesis.setMorbi(source.getMorbi());
        return anamnesis;
    }
}
