package com.jupiter.asclepi.core.repository.entity.diagnosis;

import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;
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
@Entity
public class Diagnosis implements CustomPersistable<DiagnosisId> {

    @EmbeddedId
    private DiagnosisId id;

    @ManyToOne
    @MapsId("diseaseHistoryId")
    private DiseaseHistory diseaseHistory;

    @Column(nullable = false)
    private String disease;

    private String complications;

    private String etiologyAndPathogenesis;

    private String specialityOfCourse;

    @Column(nullable = false)
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
