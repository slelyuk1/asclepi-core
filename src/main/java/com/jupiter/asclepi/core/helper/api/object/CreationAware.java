package com.jupiter.asclepi.core.helper.api.object;

import java.util.Date;

public interface CreationAware<T> {
    T getCreator();

    void setCreator(T creator);

    Date getCreatedWhen();

    void setCreatedWhen(Date createdWhen);
}
