package com.jupiter.asclepi.core.service.helper.api.v2;

import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public abstract class AbstractService<EntityType extends CustomPersistable<IdType>, IdType extends Serializable>
        implements Service<EntityType, IdType> {

    private final ConversionService conversionService;
    private final JpaRepository<EntityType, IdType> repository;

    public AbstractService(ConversionService conversionService, JpaRepository<EntityType, IdType> repository) {
        this.conversionService = conversionService;
        this.repository = repository;
    }

    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }

    // todo use custom interface
    @Override
    public JpaRepository<EntityType, IdType> getRepository() {
        return repository;
    }
}
