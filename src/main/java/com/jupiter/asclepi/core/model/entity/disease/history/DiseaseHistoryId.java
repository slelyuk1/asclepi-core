package com.jupiter.asclepi.core.model.entity.disease.history;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@Data
public class DiseaseHistoryId implements Serializable {
    private BigInteger client;
    private Integer number;
}
