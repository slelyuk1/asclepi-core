package com.jupiter.asclepi.core.helper.api.business.service;

import io.vavr.control.Try;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CreateService<RequestType, CreatedType> {
    Try<CreatedType> create(@Valid @NotNull RequestType createRequest);
}
