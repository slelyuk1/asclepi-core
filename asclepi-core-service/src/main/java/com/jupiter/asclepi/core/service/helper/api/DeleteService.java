package com.jupiter.asclepi.core.service.helper.api;

import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import org.springframework.data.domain.Persistable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public interface DeleteService<RequestType, EntityType extends Persistable<IdType>, IdType>
        extends Service<EntityType, IdType> {

    default boolean delete(@Valid @NotNull RequestType deleteRequest) {
        IdType toDeleteId = Objects.requireNonNull(getConversionService().convert(deleteRequest, getIdClass()));
        return getRepository().findById(toDeleteId)
                .map(toDelete -> {
                    getRepository().delete(toDelete);
                    return true;
                })
                .orElse(false);
    }

    default void deleteOrThrow(@Valid @NotNull RequestType deleteRequest) {
        if (delete(deleteRequest)) {
            return;
        }
        throw new NonExistentIdException(getEntityName(), deleteRequest);
    }

}
