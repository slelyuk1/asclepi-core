package com.jupiter.asclepi.core.service.helper.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface DeleteService<RequestType, ResponseType> {
    ResponseType delete(@Valid @NotNull RequestType deleteRequest);
}
