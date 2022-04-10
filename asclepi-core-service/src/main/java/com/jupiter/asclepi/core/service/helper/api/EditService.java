package com.jupiter.asclepi.core.service.helper.api;

import io.vavr.control.Try;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface EditService<RequestType, EditedType> {
    Try<EditedType> edit(@Valid @NotNull RequestType editRequest);
}
