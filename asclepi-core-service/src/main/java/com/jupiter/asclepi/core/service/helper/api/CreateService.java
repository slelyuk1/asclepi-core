package com.jupiter.asclepi.core.service.helper.api;

import io.vavr.control.Try;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CreateService<RequestType, CreatedType> {
    Try<CreatedType> create(@Valid @NotNull RequestType createRequest);
}
