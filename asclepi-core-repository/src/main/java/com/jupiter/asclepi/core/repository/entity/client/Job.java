package com.jupiter.asclepi.core.repository.entity.client;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class Job implements Serializable {

    @Column(name = "job_name")
    private String name;
    @Column(name = "job_organization")
    private String organization;

}
