package com.jupiter.asclepi.core.web.helper.api;

import org.springframework.core.convert.ConversionService;

public abstract class AbstractController<ResponseType> implements Controller<ResponseType> {

    private final ConversionService conversionService;

    protected AbstractController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }

}
