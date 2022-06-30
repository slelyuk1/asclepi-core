package com.jupiter.asclepi.core.repository.entity.analysis;

import com.jupiter.asclepi.core.repository.entity.Document;
import com.jupiter.asclepi.core.repository.entity.visit.Visit;
import com.jupiter.asclepi.core.repository.helper.api.CreationAware;
import com.jupiter.asclepi.core.repository.helper.api.CreationData;
import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Analysis implements CustomPersistable<AnalysisId>, CreationAware<String> {

    @EmbeddedId
    private AnalysisId id;

    @ManyToOne
    @MapsId("visitId")
    private Visit visit;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String summary;

    @OneToMany
    private List<Document> documents;

    @Embedded
    private CreationData<String> creation;

    public Analysis() {
        id = new AnalysisId();
        documents = new ArrayList<>();
        creation = new CreationData<>();
    }

    public void setId(AnalysisId id) {
        this.id = id;
        if (id.getVisitId() != null) {
            setVisit(Visit.fromId(id.getVisitId()));
        }
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
        getId().setVisitId(visit.getId());
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
