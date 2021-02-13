package com.jupiter.asclepi.core.helper.api.business.shared;

public interface Editing<RequestType, EditedType> {
    EditedType edit(RequestType editRequest);
}
