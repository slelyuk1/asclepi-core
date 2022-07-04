package com.jupiter.asclepi.core.service.helper.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Deprecated
public interface GetService<RequestType, GettingType> {
    Optional<GettingType> getOne(@Valid @NotNull RequestType getRequest);
}
