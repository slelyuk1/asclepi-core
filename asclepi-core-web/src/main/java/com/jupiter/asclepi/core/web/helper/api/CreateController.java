package com.jupiter.asclepi.core.web.helper.api;

import com.jupiter.asclepi.core.service.helper.api.CreateService;
import org.springframework.data.domain.Persistable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CreateController<RequestType, EntityType extends Persistable<IdType>, IdType, ResponseType>
        extends Controller<ResponseType> {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default ResponseType create(RequestType createRequest) {
        EntityType entity = getServiceForCreation().create(createRequest);
        return getConversionService().convert(entity, getResponseClass());
    }

    CreateService<RequestType, EntityType, IdType> getServiceForCreation();

}
