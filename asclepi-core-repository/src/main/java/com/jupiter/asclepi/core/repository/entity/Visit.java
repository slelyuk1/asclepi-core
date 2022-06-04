package com.jupiter.asclepi.core.repository.entity;

import com.jupiter.asclepi.core.repository.entity.id.VisitId;
import com.jupiter.asclepi.core.repository.helper.api.AbstractCreationAware;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class Visit extends AbstractCreationAware<Employee> {

    @EmbeddedId
    private VisitId id;

    @ManyToOne
    @MapsId("diseaseHistoryId")
    private DiseaseHistory diseaseHistory;

    @NotNull
    private LocalDateTime when;

    public static Visit fromId(VisitId id) {
        Visit toReturn = new Visit();
        toReturn.setId(id);
        return toReturn;
    }

    public Visit() {
        id = new VisitId();
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
