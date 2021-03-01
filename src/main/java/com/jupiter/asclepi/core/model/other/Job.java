package com.jupiter.asclepi.core.model.other;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Job {
    private String name;
    private String organization;
}
