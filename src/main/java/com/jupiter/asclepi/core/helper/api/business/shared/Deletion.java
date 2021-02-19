package com.jupiter.asclepi.core.helper.api.business.shared;

import javax.validation.constraints.NotNull;

public interface Deletion<RequestType, ResponseType> {
    ResponseType delete(@NotNull RequestType deleteRequest);
}
