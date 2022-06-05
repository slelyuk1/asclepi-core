package com.jupiter.asclepi.core.model.request.visit;

import com.jupiter.asclepi.core.model.request.history.GetDiseaseHistoryRequest;
import lombok.Data;
import lombok.SneakyThrows;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateVisitRequest {
    @Valid
    @NotNull
    private GetDiseaseHistoryRequest diseaseHistory;

    @Future
    private LocalDateTime when;

}
