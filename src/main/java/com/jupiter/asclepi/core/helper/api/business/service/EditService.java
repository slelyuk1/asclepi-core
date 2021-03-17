package com.jupiter.asclepi.core.helper.api.business.service;

import io.vavr.control.Try;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface EditService<RequestType, EditedType> {
    Try<EditedType> edit(@Valid @NotNull RequestType editRequest);
}
