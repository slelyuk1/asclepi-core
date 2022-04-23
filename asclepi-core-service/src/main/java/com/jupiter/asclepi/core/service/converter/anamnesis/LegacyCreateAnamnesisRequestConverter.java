package com.jupiter.asclepi.core.service.converter.anamnesis;

import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.request.disease.anamnesis.CreateAnamnesisRequest;
import org.springframework.core.convert.converter.Converter;

public class LegacyCreateAnamnesisRequestConverter implements Converter<CreateAnamnesisRequest, Anamnesis> {
    @Override
    public Anamnesis convert(CreateAnamnesisRequest source) {
        Anamnesis anamnesis = new Anamnesis();
        anamnesis.setComplaints(source.getComplaints());
        anamnesis.setVitae(source.getVitae());
        anamnesis.setMorbi(source.getMorbi());
        return anamnesis;
    }
}
