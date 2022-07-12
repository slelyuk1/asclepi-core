package com.jupiter.asclepi.core.service.helper.api;

import org.springframework.data.domain.Persistable;

public interface CrudService<
        GetRequestType,
        CreateRequestType,
        EditRequestType,
        EntityType extends Persistable<IdType>, IdType
        > extends
        GetService<GetRequestType, EntityType, IdType>,
        GetAllService<EntityType, IdType>,
        CreateService<CreateRequestType, EntityType, IdType>,
        EditService<EditRequestType, EntityType, IdType>,
        DeleteService<GetRequestType, EntityType, IdType> {
}
