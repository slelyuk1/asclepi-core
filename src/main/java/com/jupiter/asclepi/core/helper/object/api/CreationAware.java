package com.jupiter.asclepi.core.helper.object.api;

import java.util.Date;

public interface CreationAware<T> {
    void setCreator(T creator);

    void setCreatedWhen(Date createdWhen);

    T getCreator();

    Date getCreatedWhen();
}
