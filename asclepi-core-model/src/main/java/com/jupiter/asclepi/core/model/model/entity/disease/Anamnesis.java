package com.jupiter.asclepi.core.model.model.entity.disease;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@Entity
public class Anamnesis {

    @Id
    private BigInteger id;

    @ManyToOne(optional = false)
    private DiseaseHistory diseaseHistory;

    @NotEmpty
    private String complaints;

    @NotEmpty
    private String morbi;

    @NotEmpty
    private String vitae;

    public Anamnesis() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Anamnesis anamnesis = (Anamnesis) o;
        return new EqualsBuilder().append(getId(), anamnesis.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
