package com.jupiter.asclepi.core.model.request.disease.history;

import lombok.Data;

@Data
public class GetDiseaseHistoryRequest {
    private Integer clientId;
    private Integer number;
}
