package com.jupiter.asclepi.core.service.helper.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Deprecated
public interface CreateService<T, R> {
    R create(@Valid @NotNull T createRequest);
}
