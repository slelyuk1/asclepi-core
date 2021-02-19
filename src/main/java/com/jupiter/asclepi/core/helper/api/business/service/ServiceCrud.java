package com.jupiter.asclepi.core.helper.api.business.service;

import com.jupiter.asclepi.core.helper.api.business.shared.*;
import io.vavr.control.Try;

import java.util.Optional;

public interface ServiceCrud<GetRequestType, CreateRequestType, EditRequestType, ResponseType, DeleteResponseType> extends
        Getting<GetRequestType, Optional<ResponseType>>,
        GettingAll<ResponseType>,
        Creation<CreateRequestType, Try<ResponseType>>,
        Editing<EditRequestType, Try<ResponseType>>,
        Deletion<GetRequestType, DeleteResponseType> {
}
