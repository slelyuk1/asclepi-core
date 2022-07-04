package com.jupiter.asclepi.core.service.helper.api.v2;

import org.springframework.data.domain.Persistable;

public interface CrudService<
        GetRequestType,
        CreateRequestType,
        EditRequestType,
        EntityType extends Persistable<IdType>, IdType,
        DeleteResponseType
        > extends
        GetService<GetRequestType, EntityType, IdType>,
        GetAllService<EntityType, IdType>,
        CreateService<CreateRequestType, EntityType, IdType>,
        EditService<EditRequestType, EntityType>,
        DeleteService<GetRequestType, EntityType, IdType> {
}
