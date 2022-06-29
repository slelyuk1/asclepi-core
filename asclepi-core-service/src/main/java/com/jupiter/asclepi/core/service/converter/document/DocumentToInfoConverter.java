package com.jupiter.asclepi.core.service.converter.document;

import com.jupiter.asclepi.core.model.response.DocumentInfo;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface DocumentToInfoConverter extends Converter<Document, DocumentInfo> {

    @Override
    @Mapping(target = "analysis", source = "analysis.id")
    DocumentInfo convert(Document source);

}
