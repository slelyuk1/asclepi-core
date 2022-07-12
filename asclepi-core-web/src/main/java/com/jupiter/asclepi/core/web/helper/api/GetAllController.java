package com.jupiter.asclepi.core.web.helper.api;

import com.jupiter.asclepi.core.service.helper.api.GetAllService;
import org.springframework.data.domain.Persistable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SuppressWarnings("unused")
public interface GetAllController<ResponseType, EntityType extends Persistable<IdType>, IdType> extends Controller<ResponseType> {
    @GetMapping("/all")
    default List<ResponseType> getAll() {
        return getServiceForGetAll().getAll().stream()
                .map(entity -> getConversionService().convert(entity, getResponseClass()))
                .toList();
    }

    GetAllService<EntityType, IdType> getServiceForGetAll();
}
