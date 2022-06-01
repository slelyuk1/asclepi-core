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
@Entity(name = "anamnesis")
public class Anamnesis {

    @Id
    @Column(name = "id")
    private BigInteger id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disease_history_client_id")
    @JoinColumn(name = "disease_history_number")
    private DiseaseHistory diseaseHistory;

    @NotEmpty
    @Column(name = "complaints")
    private String complaints;

    @NotEmpty
    @Column(name = "morbi")
    private String morbi;

    @NotEmpty
    @Column(name = "vitae")
    private String vitae;

    public Anamnesis(@NotNull BigInteger id) {
        this.id = id;
    }

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
