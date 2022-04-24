package com.jupiter.asclepi.core.service.configuration;

import com.jupiter.asclepi.core.service.converter.ConversionServiceAdapter;
import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;

@MapperConfig(
        componentModel = "spring",
        uses = ConversionServiceAdapter.class
)
@SpringMapperConfig(conversionServiceAdapterPackage = "com.jupiter.asclepi.core.service.converter")
public interface MappingConfiguration {
}
