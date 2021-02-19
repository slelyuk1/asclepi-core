package com.jupiter.asclepi.core.helper.api.business.shared;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface Creation<RequestType, CreatedType> {
    CreatedType create(@Valid @NotNull RequestType createRequest);
}
