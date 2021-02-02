package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.helper.object.api.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;

@Data
public class DiseaseHistory extends AbstractCreationAware<Employee> {
    private Client client;
    private Integer number;
    private Diagnosis diagnosis;
    private Employee doctor;
}
