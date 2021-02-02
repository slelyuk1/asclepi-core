package com.jupiter.asclepi.core.helper.service.api;

public interface Deletion<RequestType> {
    // todo response
    void delete(RequestType deleteRequest);
}
