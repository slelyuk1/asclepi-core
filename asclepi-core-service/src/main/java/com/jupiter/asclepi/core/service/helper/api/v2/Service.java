package com.jupiter.asclepi.core.service.helper.api.v2;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Service<EntityType extends Persistable<IdType>, IdType> {

    Class<EntityType> getEntityClass();

    Class<IdType> getIdClass();

    ConversionService getConversionService();

    JpaRepository<EntityType, IdType> getRepository();

}
