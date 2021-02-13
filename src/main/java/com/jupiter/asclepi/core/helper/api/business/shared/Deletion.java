package com.jupiter.asclepi.core.helper.api.business.shared;

public interface Deletion<RequestType, ResponseType> {
    // todo response
    ResponseType delete(RequestType deleteRequest);
}
