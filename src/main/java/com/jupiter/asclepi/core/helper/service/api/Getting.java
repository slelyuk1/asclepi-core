package com.jupiter.asclepi.core.helper.service.api;

import java.util.List;

public interface Getting<RequestType, GettingType> {
    GettingType getOne(RequestType getRequest);

    List<GettingType> getAll();
}
