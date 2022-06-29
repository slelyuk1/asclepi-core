package com.jupiter.asclepi.core.service.converter.document;

import com.jupiter.asclepi.core.model.request.document.EditDocumentRequest;
import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.service.configuration.MappingConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(config = MappingConfiguration.class)
public interface EditRequestToDocumentConverter extends Converter<EditDocumentRequest, Document> {

    @Override
    @Mapping(target = "path", expression = "java(null)")
    @Mapping(target = "analysis", expression = "java(null)")
    Document convert(EditDocumentRequest request);

}
