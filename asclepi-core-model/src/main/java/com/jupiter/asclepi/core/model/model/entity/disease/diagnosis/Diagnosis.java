package com.jupiter.asclepi.core.model.model.entity.disease.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@Entity(name = "diagnosis")
@IdClass(DiagnosisId.class)
public class Diagnosis {

    @Id
    @Column(name = "client_id")
    private BigInteger clientId;

    @Id
    @Column(name = "disease_history_number")
    private Integer diseaseHistoryNumber;

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "number")
    private Integer number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false, insertable = false)
    @JoinColumn(name = "disease_history_number", updatable = false, insertable = false)
    private DiseaseHistory diseaseHistory;

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

    public Diagnosis(@NotNull DiagnosisId id) {
        setId(id);
    }

    public Diagnosis() {
    }

    public final void setId(DiagnosisId id) {
        clientId = id.getClientId();
        diseaseHistoryNumber = id.getDiseaseHistoryNumber();
        number = id.getNumber();
        diseaseHistory = new DiseaseHistory();
        diseaseHistory.setId(new DiseaseHistoryId(clientId, diseaseHistoryNumber));
    }

    public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
        clientId = diseaseHistory.getId().getClient();
        diseaseHistoryNumber = diseaseHistory.getNumber();
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
        return new EqualsBuilder()
                .append(getClientId(), diagnosis.getClientId())
                .append(getDiseaseHistoryNumber(), diagnosis.getDiseaseHistoryNumber())
                .append(getNumber(), diagnosis.getNumber())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getClientId())
                .append(getDiseaseHistoryNumber())
                .append(getNumber())
                .toHashCode();
    }

}
