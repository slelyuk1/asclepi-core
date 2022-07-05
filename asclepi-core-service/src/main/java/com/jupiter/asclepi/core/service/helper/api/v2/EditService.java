package com.jupiter.asclepi.core.service.helper.api.v2;

import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;
import com.jupiter.asclepi.core.service.exception.shared.NonExistentIdException;
import com.jupiter.asclepi.core.service.util.CustomBeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public interface EditService<RequestType, EntityType extends CustomPersistable<IdType>, IdType extends Serializable>
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
                .orElseThrow(() -> new NonExistentIdException(toCopyFrom.getEntityName(), toCopyFrom.getId()));
    }

    default void preProcessBeforeEditing(RequestType request, EntityType editedEntity, EntityType toCopyFrom) {
    }

}
