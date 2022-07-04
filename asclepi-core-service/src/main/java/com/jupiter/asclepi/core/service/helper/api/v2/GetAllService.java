package com.jupiter.asclepi.core.service.helper.api.v2;

import org.springframework.data.domain.Persistable;

import java.util.List;

public interface GetAllService<EntityType extends Persistable<IdType>, IdType> extends Service<EntityType, IdType> {

    default List<EntityType> getAll() {
        return postProcessAfterGetAll(getRepository().findAll());
    }

    default List<EntityType> postProcessAfterGetAll(List<EntityType> allFound) {
        return allFound;
    }

}
