package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Consultation extends AbstractCreationAware<Employee> {
    @EqualsAndHashCode.Include
    private Visit visit;
    @EqualsAndHashCode.Include
    private Integer number;
    private Anamnesis anamnesis;
    private String inspection;
}
