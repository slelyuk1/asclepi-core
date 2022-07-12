package com.jupiter.asclepi.core.service.helper.api;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Persistable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public interface CreateService<RequestType, EntityType extends Persistable<IdType>, IdType>
        extends Service<EntityType, IdType> {

    default EntityType create(@Valid @NotNull RequestType request) {
        ConversionService conversionService = getConversionService();
        EntityType toCreate = Objects.requireNonNull(conversionService.convert(request, getEntityClass()));
        preProcessBeforeCreation(request, toCreate);
        return getRepository().saveAndFlush(toCreate);
    }

    default void preProcessBeforeCreation(RequestType request, EntityType toCreate) {
    }

}
