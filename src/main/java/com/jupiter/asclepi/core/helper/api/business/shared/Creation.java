package com.jupiter.asclepi.core.helper.api.business.shared;

public interface Creation<RequestType, CreatedType> {
    CreatedType create(RequestType createRequest);
}
