package com.jupiter.asclepi.core.web.helper.api;

import com.jupiter.asclepi.core.service.helper.api.EditService;
import org.springframework.data.domain.Persistable;
import org.springframework.web.bind.annotation.PatchMapping;

@SuppressWarnings("unused")
public interface PatchController<RequestType, EntityType extends Persistable<IdType>, IdType, ResponseType> extends Controller<ResponseType> {

    @PatchMapping
    default ResponseType edit(RequestType editRequest) {
        EntityType edited = getServiceForPatch().edit(editRequest);
        return getConversionService().convert(edited, getResponseClass());
    }

    EditService<RequestType, EntityType, IdType> getServiceForPatch();
}
