package com.jupiter.asclepi.core.web.helper.api;

import com.jupiter.asclepi.core.service.helper.api.*;
import org.springframework.data.domain.Persistable;

public interface CrudController<EntityType extends Persistable<IdType>, IdType,
        GetRequestType, CreateRequestType, EditRequestType, ResponseType> extends
        GetController<GetRequestType, EntityType, IdType, ResponseType>,
        GetAllController<ResponseType, EntityType, IdType>,
        CreateController<CreateRequestType, EntityType, IdType, ResponseType>,
        PatchController<EditRequestType, EntityType, IdType, ResponseType>,
        DeleteController<GetRequestType, EntityType, IdType> {

    CrudService<GetRequestType, CreateRequestType, EditRequestType, EntityType, IdType> getCrudService();

    @Override
    default CreateService<CreateRequestType, EntityType, IdType> getServiceForCreation() {
        return getCrudService();
    }

    @Override
    default GetAllService<EntityType, IdType> getServiceForGetAll() {
        return getCrudService();
    }

    @Override
    default EditService<EditRequestType, EntityType, IdType> getServiceForPatch() {
        return getCrudService();
    }

    @Override
    default DeleteService<GetRequestType, EntityType, IdType> getServiceForDelete() {
        return getCrudService();
    }

    @Override
    default GetService<GetRequestType, EntityType, IdType> getServiceForGet() {
        return getCrudService();
    }
}
