package com.jupiter.asclepi.core.helper.service.api;

public interface Creation<RequestType, CreatedType> {
    CreatedType create(RequestType createRequest);
}
