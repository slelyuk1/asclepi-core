package com.jupiter.asclepi.core.web.helper.api;

import com.jupiter.asclepi.core.service.helper.api.DeleteService;
import org.springframework.data.domain.Persistable;
import org.springframework.web.bind.annotation.DeleteMapping;

public interface DeleteController<RequestType, EntityType extends Persistable<IdType>, IdType> {

    default void delete(RequestType deleteRequest) {
        getServiceForDelete().deleteOrThrow(deleteRequest);
    }

    DeleteService<RequestType, EntityType, IdType> getServiceForDelete();

}
