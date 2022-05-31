package com.jupiter.asclepi.core.model.model.entity.disease.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseHistoryId implements Serializable {
    private BigInteger client;
    private Integer number;
}
