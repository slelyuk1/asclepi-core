package com.jupiter.asclepi.core.service.helper.api;

import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;
import org.springframework.data.domain.Persistable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public interface EditService<RequestType, EntityType extends Persistable<IdType>, IdType>
        extends Service<EntityType, IdType> {

    default EntityType edit(@Valid @NotNull RequestType editRequest) {
        EntityType toCopyFrom = Objects.requireNonNull(getConversionService().convert(editRequest, getEntityClass()));
        EntityType existing = getEditedEntity(editRequest, toCopyFrom);
        preProcessBeforeEditing(editRequest, existing, toCopyFrom);
        CustomBeanUtils.copyPropertiesWithoutNull(toCopyFrom, existing);
        return getRepository().save(existing);
    }

    default EntityType getEditedEntity(RequestType request, EntityType toCopyFrom) {
        return getRepository().findById(toCopyFrom.getId())
                .orElseThrow(() -> new NonExistentIdException(getEntityName(), toCopyFrom.getId()));
    }

    default void preProcessBeforeEditing(RequestType request, EntityType editedEntity, EntityType toCopyFrom) {
    }

}
