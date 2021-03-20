package com.jupiter.asclepi.core.model.entity.disease.diagnosis;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(DiagnosisId.class)
public class Diagnosis {
    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private DiseaseHistory diseaseHistory;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer number;

    private String disease;

    private String complications;

    private String etiologyAndPathogenesis;

    private String specialityOfCourse;

    private Boolean isFinal;
}
