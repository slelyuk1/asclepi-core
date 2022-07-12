package com.jupiter.asclepi.core.repository.entity;

import com.jupiter.asclepi.core.repository.entity.diseasehistory.DiseaseHistory;
import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@Entity
public class Anamnesis implements CustomPersistable<BigInteger> {

    @Id
    @GeneratedValue
    private BigInteger id;

    @ManyToOne(optional = false)
    private DiseaseHistory diseaseHistory;

    @Column(nullable = false)
    private String complaints;

    @Column(nullable = false)
    private String morbi;

    @Column(nullable = false)
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
