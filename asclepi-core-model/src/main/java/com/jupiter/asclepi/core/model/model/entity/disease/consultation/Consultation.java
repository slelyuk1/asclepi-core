package com.jupiter.asclepi.core.model.model.entity.disease.consultation;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.Anamnesis;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import com.jupiter.asclepi.core.model.support.mapstruct.ConstructorProperties;
import com.jupiter.asclepi.core.model.support.mapstruct.Default;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(ConsultationId.class)
public class Consultation extends AbstractCreationAware<Employee> {

    @Id
    @EqualsAndHashCode.Include
    private BigInteger clientId;

    @Id
    @EqualsAndHashCode.Include
    private Integer diseaseHistoryNumber;

    @Id
    @EqualsAndHashCode.Include
    private Integer visitNumber;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer number;

    @NotNull
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "clientId", updatable = false, insertable = false),
            @JoinColumn(name = "diseaseHistoryNumber", updatable = false, insertable = false),
            @JoinColumn(name = "visitNumber", updatable = false, insertable = false)
    })
    private Visit visit;

    @NotNull
    @ManyToOne
    private Anamnesis anamnesis;

    @NotEmpty
    private String inspection;

    @Default
    @ConstructorProperties
    public Consultation(@NotNull ConsultationId id) {
        clientId = id.getClientId();
        diseaseHistoryNumber = id.getDiseaseHistoryNumber();
        visitNumber = id.getVisitNumber();
        number = id.getNumber();
        this.visit = new Visit(new VisitId(new DiseaseHistoryId(clientId, diseaseHistoryNumber), visitNumber));
    }

    public Integer getNumber() {
        return number;
    }

    public Visit getVisit() {
        return visit;
    }

    public Anamnesis getAnamnesis() {
        return anamnesis;
    }

    public String getInspection() {
        return inspection;
    }

    public void setVisit(@NotNull Visit visit) {
        this.visit = visit;
        clientId = visit.getDiseaseHistory().getClient().getId();
        diseaseHistoryNumber = visit.getDiseaseHistory().getNumber();
        visitNumber = visit.getNumber();
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setAnamnesis(Anamnesis anamnesis) {
        this.anamnesis = anamnesis;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }
}
