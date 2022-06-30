package com.jupiter.asclepi.core.repository.helper.api;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

public interface CustomPersistable<T extends Serializable> extends Persistable<T> {

    @Override
    default boolean isNew() {
        return null == getId();
    }

}
