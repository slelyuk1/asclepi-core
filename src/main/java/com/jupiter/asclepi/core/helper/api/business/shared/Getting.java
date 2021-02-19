package com.jupiter.asclepi.core.helper.api.business.shared;

import javax.validation.constraints.NotNull;

public interface Getting<RequestType, GettingType> {
    GettingType getOne(@NotNull RequestType getRequest);
}
