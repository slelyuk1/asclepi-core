package com.jupiter.asclepi.core.model.model.other;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Job implements Cloneable {
    @Column(name = "job_name")
    private String name;
    @Column(name = "organization")
    private String organization;

    @Override
    public Job clone() {
        try {
            return (Job) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("CloneNotSupportedException cannot be thrown here", e);
        }
    }
}
