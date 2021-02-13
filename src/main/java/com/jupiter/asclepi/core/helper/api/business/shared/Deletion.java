package com.jupiter.asclepi.core.helper.api.business.shared;

public interface Deletion<RequestType, ResponseType> {
    ResponseType delete(RequestType deleteRequest);
}
