package com.jupiter.asclepi.core.helper.api.object;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.PrePersist;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class AbstractCreationAware<T> implements CreationAware<T> {

    private T creator;
    private Date createdWhen;

    @Override
    public T getCreator() {
        return creator;
    }

    @Override
    public void setCreator(T creator) {
        this.creator = creator;
    }

    @Override
    public Date getCreatedWhen() {
        return createdWhen;
    }

    @Override
    public void setCreatedWhen(Date createdWhen) {
        this.createdWhen = createdWhen;
    }

    @PrePersist
    void prePersist() {
        setCreatedWhen(new Date());
        // todo set creator and its constraint when security implemented
    }
}
