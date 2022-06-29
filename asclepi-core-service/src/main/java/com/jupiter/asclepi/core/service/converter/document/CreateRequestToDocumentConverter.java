package com.jupiter.asclepi.core.service.converter.document;

import com.jupiter.asclepi.core.model.request.document.CreateDocumentRequest;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface CreateRequestToDocumentConverter extends Converter<CreateDocumentRequest, Document> {

    @Override
    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "analysis.id", source = "analysis")
    Document convert(CreateDocumentRequest request);

}
