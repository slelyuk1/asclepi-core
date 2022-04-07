package com.jupiter.asclepi.core.model.model.other;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Job implements Cloneable {
    private String name;
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
