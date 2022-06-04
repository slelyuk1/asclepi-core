package com.jupiter.asclepi.core.repository.entity;

import com.jupiter.asclepi.core.repository.entity.id.DiagnosisId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
public class Diagnosis {

    @EmbeddedId
    private DiagnosisId id;

    @ManyToOne
    @MapsId("diseaseHistoryId")
    private DiseaseHistory diseaseHistory;

    @NotNull
    private String disease;

    private String complications;

    private String etiologyAndPathogenesis;

    private String specialityOfCourse;

    @NotNull
    private Boolean isFinal;

    public Diagnosis() {
        id = new DiagnosisId();
    }

    public void setId(DiagnosisId id) {
        this.id = id;
        setDiseaseHistory(DiseaseHistory.fromId(id.getDiseaseHistoryId()));
    }

    public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
        getId().setDiseaseHistoryId(diseaseHistory.getId());
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
