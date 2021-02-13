package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Consultation extends AbstractCreationAware<Employee> {
    private BigInteger id;
    private Visit visit;
    private Anamnesis anamnesis;
    private String inspection;
}
