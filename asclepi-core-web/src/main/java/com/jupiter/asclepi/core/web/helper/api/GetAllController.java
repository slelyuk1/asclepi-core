package com.jupiter.asclepi.core.web.helper.api;

import java.util.List;

@SuppressWarnings("unused")
public interface GetAllController<GettingType> {
    List<GettingType> getAll();
}
