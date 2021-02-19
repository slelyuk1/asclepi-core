package com.jupiter.asclepi.core.helper.api.business.shared;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface Editing<RequestType, EditedType> {
    EditedType edit(@Valid @NotNull RequestType editRequest);
}
