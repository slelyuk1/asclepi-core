package com.jupiter.asclepi.core.helper.api.business.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CreateService<RequestType, CreatedType> {
    CreatedType create(@Valid @NotNull RequestType createRequest);
}
