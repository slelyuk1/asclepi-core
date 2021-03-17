package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
public class Diagnosis {
    @Id
    @GeneratedValue
    private BigInteger id;
    @OneToOne
    private DiseaseHistory diseaseHistory;
    private String disease;
    private String complications;
    private String etiologyAndPathogenesis;
    private Boolean isFinal;
}
