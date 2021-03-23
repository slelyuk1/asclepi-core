package com.jupiter.asclepi.core.model.entity.disease.diagnosis;

import com.jupiter.asclepi.core.model.entity.disease.history.DiseaseHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(DiagnosisId.class)
public class Diagnosis {
    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    @NotNull
    private DiseaseHistory diseaseHistory;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @NotNull
    private Integer number;

    @NotNull
    private String disease;

    private String complications;

    private String etiologyAndPathogenesis;

    private String specialityOfCourse;

    @NotNull
    private Boolean isFinal;
}
