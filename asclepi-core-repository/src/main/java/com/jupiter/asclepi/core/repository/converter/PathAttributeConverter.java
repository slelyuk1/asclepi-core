package com.jupiter.asclepi.core.repository.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.file.Path;

@Converter(autoApply = true)
public class PathAttributeConverter implements AttributeConverter<Path, String> {

    @Override
    public String convertToDatabaseColumn(Path path) {
        return path.toString();
    }

    @Override
    public Path convertToEntityAttribute(String pathString) {
        return Path.of(pathString);
    }
}
