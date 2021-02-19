package com.jupiter.asclepi.core.helper.api.business.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface GetService<RequestType, GettingType> {
    GettingType getOne(@Valid @NotNull RequestType getRequest);
}
