package com.jupiter.asclepi.core.service.helper.api.v2;

import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;
import org.springframework.core.convert.ConversionService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public interface CreateService<RequestType, EntityType extends CustomPersistable<IdType>, IdType extends Serializable>
        extends Service<EntityType, IdType> {

    default EntityType create(@Valid @NotNull RequestType request) {
        ConversionService conversionService = getConversionService();
        EntityType toCreate = Objects.requireNonNull(conversionService.convert(request, getEntityClass()));
        preProcessBeforeCreation(request, toCreate);
        return getRepository().saveAndFlush(toCreate);
    }

    Class<EntityType> getEntityClass();

    default void preProcessBeforeCreation(RequestType request, EntityType toCreate) {
    }

}
