package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;

import java.util.List;

@Data
public class DiseaseHistory extends AbstractCreationAware<Employee> {
    private Client client;
    private Integer number;
    private Employee doctor;
    List<Diagnosis> diagnoses;
}
