package com.jupiter.asclepi.core.repository.entity.visit;

import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.helper.api.CreationAware;
import com.jupiter.asclepi.core.repository.helper.api.CreationData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Visit implements CreationAware<String> {

    @EmbeddedId
    private VisitId id;

    @ManyToOne
    @MapsId("diseaseHistoryId")
    private DiseaseHistory diseaseHistory;

    @NotNull
    private LocalDateTime when;

    @Embedded
    @AttributeOverride(name = "when", column = @Column(name = "created_when"))
    private CreationData<String> creation;

    public static Visit fromId(VisitId id) {
        Visit toReturn = new Visit();
        toReturn.setId(id);
        return toReturn;
    }

    public Visit() {
        id = new VisitId();
        creation = new CreationData<>();
    }

    public void setId(VisitId id) {
        this.id = id;
        if (id.getDiseaseHistoryId() != null) {
            setDiseaseHistory(DiseaseHistory.fromId(id.getDiseaseHistoryId()));
        }
    }

    public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
        id.setDiseaseHistoryId(diseaseHistory.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Visit visit = (Visit) o;
        return new EqualsBuilder().append(getId(), visit.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
