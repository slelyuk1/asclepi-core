package com.jupiter.asclepi.core.service.helper.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Deprecated
public interface EditService<RequestType, EditedType> {
    EditedType edit(@Valid @NotNull RequestType editRequest);
}
