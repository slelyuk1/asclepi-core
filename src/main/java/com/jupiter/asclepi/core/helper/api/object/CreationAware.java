package com.jupiter.asclepi.core.helper.api.object;

import java.util.Date;

public interface CreationAware<T> {
    void setCreator(T creator);

    void setCreatedWhen(Date createdWhen);

    T getCreator();

    Date getCreatedWhen();
}
