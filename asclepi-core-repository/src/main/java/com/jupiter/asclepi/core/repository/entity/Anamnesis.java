package com.jupiter.asclepi.core.repository.entity;

import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@Entity
public class Anamnesis {

    @Id
    @GeneratedValue
    private BigInteger id;

    @ManyToOne(optional = false)
    private DiseaseHistory diseaseHistory;

    @NotEmpty
    private String complaints;

    @NotEmpty
    private String morbi;

    @NotEmpty
    private String vitae;

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
