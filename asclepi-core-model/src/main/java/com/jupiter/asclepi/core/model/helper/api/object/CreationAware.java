package com.jupiter.asclepi.core.model.helper.api.object;

import java.util.Date;

@SuppressWarnings("unused")
public interface CreationAware<T> {
    T getCreator();

    void setCreator(T creator);

    Date getCreatedWhen();

    void setCreatedWhen(Date createdWhen);
}
