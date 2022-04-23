package com.jupiter.asclepi.core.model.model.entity.disease.diagnosis;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.support.mapstruct.ConstructorProperties;
import com.jupiter.asclepi.core.model.support.mapstruct.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(DiagnosisId.class)
public class Diagnosis {

    @Id
    @EqualsAndHashCode.Include
    private BigInteger clientId;

    @Id
    @EqualsAndHashCode.Include
    private Integer diseaseHistoryNumber;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @NotNull
    private Integer number;

    @NotNull
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "clientId", updatable = false, insertable = false),
            @JoinColumn(name = "diseaseHistoryNumber", updatable = false, insertable = false)
    })
    private DiseaseHistory diseaseHistory;

    @NotNull
    private String disease;

    private String complications;

    private String etiologyAndPathogenesis;

    private String specialityOfCourse;

    @NotNull
    private Boolean isFinal;


    @Default
    @ConstructorProperties
    public Diagnosis(@NotNull DiagnosisId id) {
        clientId = id.getClientId();
        diseaseHistoryNumber = id.getDiseaseHistoryNumber();
        number = id.getNumber();
        diseaseHistory = new DiseaseHistory(new DiseaseHistoryId(clientId, diseaseHistoryNumber));
    }

    public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
        clientId = diseaseHistory.getClient().getId();
        diseaseHistoryNumber = diseaseHistory.getNumber();
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setComplications(String complications) {
        this.complications = complications;
    }

    public void setEtiologyAndPathogenesis(String etiologyAndPathogenesis) {
        this.etiologyAndPathogenesis = etiologyAndPathogenesis;
    }

    public void setSpecialityOfCourse(String specialityOfCourse) {
        this.specialityOfCourse = specialityOfCourse;
    }

    public void setIsFinal(Boolean aFinal) {
        isFinal = aFinal;
    }
}
