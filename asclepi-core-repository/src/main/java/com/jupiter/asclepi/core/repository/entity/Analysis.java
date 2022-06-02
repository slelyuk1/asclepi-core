package com.jupiter.asclepi.core.repository.entity;

import com.jupiter.asclepi.core.repository.entity.id.AnalysisId;
import com.jupiter.asclepi.core.repository.helper.api.AbstractCreationAware;
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
public class Analysis extends AbstractCreationAware<Employee> {

    @EmbeddedId
    private AnalysisId id;

    @ManyToOne
    @MapsId("visitId")
    private Visit visit;

    private String title;

    @NotNull
    private String summary;

    // todo when documents functionality is implemented
    // private List<Document> documents;

    public static Analysis fromId(AnalysisId id) {
        Analysis toReturn = new Analysis();
        toReturn.setId(id);
        return toReturn;
    }

    public Analysis() {
        id = new AnalysisId();
    }

    public final void setId(AnalysisId id) {
        this.id = id;
        if (id.getVisitId() != null) {
            setVisit(Visit.fromId(id.getVisitId()));
        }
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
        getId().setVisitId(visit.getId());
    }

    @Deprecated
    public Integer getNumber(){
        return getId().getNumber();
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
        return new EqualsBuilder().append(getId(), analysis.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
