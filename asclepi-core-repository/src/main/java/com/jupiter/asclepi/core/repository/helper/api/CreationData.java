package com.jupiter.asclepi.core.repository.helper.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Getter
@Setter
public class CreationData<T> {

    @CreatedBy
    private T by;

    @CreatedDate
    private Date when;

}
