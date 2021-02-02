package com.jupiter.asclepi.core.helper.service.api;

public interface Editing<RequestType, EditedType> {
    EditedType edit(RequestType editRequest);
}
