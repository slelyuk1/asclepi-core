package com.jupiter.asclepi.core.service.helper.api;

import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;

import java.io.Serializable;

public interface CrudService<
        GetRequestType,
        CreateRequestType,
        EditRequestType,
        EntityType extends CustomPersistable<IdType>, IdType extends Serializable,
        DeleteResponseType // todo remove
        > extends
        GetService<GetRequestType, EntityType, IdType>,
        GetAllService<EntityType, IdType>,
        CreateService<CreateRequestType, EntityType, IdType>,
        EditService<EditRequestType, EntityType, IdType>,
        DeleteService<GetRequestType, EntityType, IdType> {
}
