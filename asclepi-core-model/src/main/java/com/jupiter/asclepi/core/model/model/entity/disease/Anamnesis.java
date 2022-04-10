package com.jupiter.asclepi.core.model.model.entity.disease;

import com.jupiter.asclepi.core.model.model.entity.disease.history.DiseaseHistory;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@NoArgsConstructor
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

    public Anamnesis(@NotNull BigInteger id) {
        this.id = id;
    }
}
