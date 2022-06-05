package com.jupiter.asclepi.core.web.helper.api;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SuppressWarnings("unused")
public interface GetAllController<GettingType> {
    @GetMapping("/all")
    List<GettingType> getAll();
}
