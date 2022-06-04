package com.jupiter.asclepi.core.repository.entity.other;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class Job implements Cloneable, Serializable {

    @Column(name = "job_name")
    private String name;
    @Column(name = "job_organization")
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
