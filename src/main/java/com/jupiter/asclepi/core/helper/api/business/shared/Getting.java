package com.jupiter.asclepi.core.helper.api.business.shared;

public interface Getting<RequestType, GettingType> {
    GettingType getOne(RequestType getRequest);
}
