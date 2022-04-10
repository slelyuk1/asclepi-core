package com.jupiter.asclepi.core.model.model.entity.disease.visit;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@IdClass(VisitId.class)
public class Visit extends AbstractCreationAware<Employee> {

    @Id
    @EqualsAndHashCode.Include
    private BigInteger clientId;

    @Id
    @EqualsAndHashCode.Include
    private Integer diseaseHistoryNumber;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer number;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "clientId", insertable = false, updatable = false),
            @JoinColumn(name = "diseaseHistoryNumber", insertable = false, updatable = false)
    })
    @EqualsAndHashCode.Include
    private DiseaseHistory diseaseHistory;

    @NotNull
    private LocalDateTime when;

    public Visit(VisitId id) {
        clientId = id.getClientId();
        diseaseHistoryNumber = id.getDiseaseHistoryNumber();
        number = id.getNumber();
        diseaseHistory = new DiseaseHistory(new DiseaseHistoryId(clientId, diseaseHistoryNumber));
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
        clientId = diseaseHistory.getClient().getId();
        diseaseHistoryNumber = diseaseHistory.getNumber();
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }

    public Integer getNumber() {
        return number;
    }

    public DiseaseHistory getDiseaseHistory() {
        return diseaseHistory;
    }

    public LocalDateTime getWhen() {
        return when;
    }
}
