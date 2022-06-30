package com.jupiter.asclepi.core.repository.helper.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class CreationData<T> {

    @CreatedBy
    private T by;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime when;

}
