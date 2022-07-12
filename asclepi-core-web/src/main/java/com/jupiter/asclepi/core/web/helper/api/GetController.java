package com.jupiter.asclepi.core.web.helper.api;

import com.jupiter.asclepi.core.service.helper.api.GetService;
import org.springframework.data.domain.Persistable;

@SuppressWarnings("unused")
public interface GetController<RequestType, EntityType extends Persistable<IdType>, IdType, ResponseType> extends Controller<ResponseType> {

    default ResponseType getOne(RequestType getRequest) {
        EntityType entity = getServiceForGet().getOneOrThrow(getRequest);
        return getConversionService().convert(entity, getResponseClass());
    }

    GetService<RequestType, EntityType, IdType> getServiceForGet();

}
