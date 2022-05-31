package com.jupiter.asclepi.core.model.model.entity.analysis;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistoryId;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.model.entity.disease.visit.VisitId;
import com.jupiter.asclepi.core.model.model.entity.people.Employee;
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
@Entity(name = "analysis")
@IdClass(AnalysisId.class)
public class Analysis extends AbstractCreationAware<Employee> {

    @Id
    @Column(name = "client_id")
    private BigInteger clientId;

    @Id
    @Column(name = "disease_history_number")
    private Integer diseaseHistoryNumber;

    @Id
    @Column(name = "visit_number")
    private Integer visitNumber;

    @Id
    @GeneratedValue
    @Column(name = "number")
    private Integer number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id", updatable = false, insertable = false)
    @JoinColumn(name = "disease_history_number", updatable = false, insertable = false)
    @JoinColumn(name = "visit_number", updatable = false, insertable = false)
    private Visit visit;

    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "summary")
    private String summary;

    // todo when documents functionality is implemented
    // private List<Document> documents;

    public Analysis(@NotNull AnalysisId id) {
        setId(id);
    }

    public Analysis() {
    }

    public final void setId(AnalysisId id) {
        clientId = id.getClientId();
        diseaseHistoryNumber = id.getDiseaseHistoryNumber();
        visitNumber = id.getVisitNumber();
        number = id.getNumber();
        visit = new Visit();
        visit.setId(new VisitId(new DiseaseHistoryId(clientId, diseaseHistoryNumber), visitNumber));
    }

    public void setVisit(@NotNull Visit visit) {
        this.visit = visit;
        clientId = visit.getDiseaseHistory().getId().getClient();
        diseaseHistoryNumber = visit.getDiseaseHistory().getNumber();
        visitNumber = visit.getNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Analysis analysis = (Analysis) o;
        return new EqualsBuilder()
                .append(getClientId(), analysis.getClientId())
                .append(getDiseaseHistoryNumber(), analysis.getDiseaseHistoryNumber())
                .append(getVisitNumber(), analysis.getVisitNumber())
                .append(getNumber(), analysis.getNumber())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getClientId())
                .append(getDiseaseHistoryNumber())
                .append(getVisitNumber())
                .append(getNumber())
                .toHashCode();
    }

}
