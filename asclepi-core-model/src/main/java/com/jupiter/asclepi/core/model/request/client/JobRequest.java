package com.jupiter.asclepi.core.model.request.client;

import lombok.Data;

@Data
public class JobRequest implements Cloneable {

    private String name;
    private String organization;

    @Override
    public JobRequest clone() {
        try {
            return (JobRequest) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("CloneNotSupportedException cannot be thrown here", e);
        }
    }
}
