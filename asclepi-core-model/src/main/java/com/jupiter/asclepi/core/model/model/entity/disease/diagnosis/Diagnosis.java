package com.jupiter.asclepi.core.model.model.entity.disease.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity(name = "diagnosis")
@IdClass(DiagnosisId.class)
public class Diagnosis {

    @Id
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JoinColumn(name = "disease_history_number")
    private DiseaseHistory diseaseHistory;

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "number")
    private Integer number;

    @NotNull
    @Column(name = "disease")
    private String disease;

    @Column(name = "complications")
    private String complications;

    @Column(name = "etiology_and_pathogenesis")
    private String etiologyAndPathogenesis;

    @Column(name = "speciality_of_course")
    private String specialityOfCourse;

    @NotNull
    @Column(name = "is_final")
    private Boolean isFinal;

    public DiagnosisId getId() {
        final DiseaseHistory diseaseHistoryToExtractId = getDiseaseHistory();
        return new DiagnosisId(
                diseaseHistoryToExtractId != null ? diseaseHistoryToExtractId.getId() : null,
                getNumber()
        );
    }

    public void setId(DiagnosisId id) {
        DiseaseHistory diseaseHistoryToSet = new DiseaseHistory();
        diseaseHistoryToSet.setId(id.getDiseaseHistory());
        setDiseaseHistory(diseaseHistoryToSet);
        setNumber(id.getNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Diagnosis diagnosis = (Diagnosis) o;
        return new EqualsBuilder().append(getId(), diagnosis.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
