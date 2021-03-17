package com.jupiter.asclepi.core.helper.api.business.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface GetService<RequestType, GettingType> {
    Optional<GettingType> getOne(@Valid @NotNull RequestType getRequest);
}
