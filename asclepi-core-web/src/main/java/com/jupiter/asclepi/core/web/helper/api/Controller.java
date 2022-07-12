package com.jupiter.asclepi.core.web.helper.api;

import org.springframework.core.convert.ConversionService;

public interface Controller <ResponseType> {

    Class<ResponseType> getResponseClass();

    ConversionService getConversionService();

}
