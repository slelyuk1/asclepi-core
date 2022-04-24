package com.jupiter.asclepi.core.model.model.entity.analysis;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@NoArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@IdClass(AnalysisId.class)
public class Analysis extends AbstractCreationAware<Employee> {

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

    private String title;

    @NotNull
    private String summary;

    public Analysis(@NotNull AnalysisId id) {
        setId(id);
    }

    public final void setId(AnalysisId id){
        clientId = id.getClientId();
        diseaseHistoryNumber = id.getDiseaseHistoryNumber();
        visitNumber = id.getVisitNumber();
        number = id.getNumber();
        this.visit = new Visit(new VisitId(new DiseaseHistoryId(clientId, diseaseHistoryNumber), visitNumber));
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // todo when documents functionality is implemented
    // private List<Document> documents;
}
