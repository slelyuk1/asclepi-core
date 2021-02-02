package com.jupiter.asclepi.core.helper.object.api;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class AbstractCreationAware<T> implements CreationAware<T> {

    private T creator;
    private Date createdWhen;

    @Override
    public void setCreator(T creator) {
        this.creator = creator;
    }

    @Override
    public void setCreatedWhen(Date createdWhen) {
        this.createdWhen = createdWhen;
    }

    @Override
    public T getCreator() {
        return creator;
    }

    @Override
    public Date getCreatedWhen() {
        return createdWhen;
    }
}
