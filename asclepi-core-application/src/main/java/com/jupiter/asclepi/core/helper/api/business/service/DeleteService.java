package com.jupiter.asclepi.core.helper.api.business.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface DeleteService<RequestType, ResponseType> {
    ResponseType delete(@Valid @NotNull RequestType deleteRequest);
}
