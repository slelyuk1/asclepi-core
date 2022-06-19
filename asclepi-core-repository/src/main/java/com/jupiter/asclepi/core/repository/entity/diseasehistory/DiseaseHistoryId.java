package com.jupiter.asclepi.core.repository.entity.diseasehistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DiseaseHistoryId implements Serializable {
    private BigInteger clientId;
    private Integer number;
}
