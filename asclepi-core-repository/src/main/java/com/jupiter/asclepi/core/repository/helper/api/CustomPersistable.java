package com.jupiter.asclepi.core.repository.helper.api;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface CustomPersistable<IdType extends Serializable> extends Persistable<IdType> {

    @Override
    default boolean isNew() {
        return null == getId();
    }

}
