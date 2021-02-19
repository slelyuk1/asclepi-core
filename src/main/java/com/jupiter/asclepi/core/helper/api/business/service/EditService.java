package com.jupiter.asclepi.core.helper.api.business.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface EditService<RequestType, EditedType> {
    EditedType edit(@Valid @NotNull RequestType editRequest);
}
