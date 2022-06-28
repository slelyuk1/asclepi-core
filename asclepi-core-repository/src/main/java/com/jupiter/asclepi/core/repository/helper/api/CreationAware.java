package com.jupiter.asclepi.core.repository.helper.api;

public interface CreationAware<T> {

    CreationData<T> getCreation();

    void setCreation(CreationData<T> creationData);

}
