package com.jupiter.asclepi.core.service.helper.api;

import org.springframework.data.domain.Persistable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

public interface GetService<RequestType, EntityType extends Persistable<IdType>, IdType> extends Service<EntityType, IdType> {

    default Optional<EntityType> getOne(@Valid @NotNull RequestType getRequest) {
        IdType id = Objects.requireNonNull(getConversionService().convert(getRequest, getIdClass()));
        return getRepository().findById(id);
    }

}
