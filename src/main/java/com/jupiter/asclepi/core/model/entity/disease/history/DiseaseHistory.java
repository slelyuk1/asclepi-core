package com.jupiter.asclepi.core.model.entity.disease.history;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.Diagnosis;
import com.jupiter.asclepi.core.model.entity.people.Client;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;

import java.util.List;

@Data
public class DiseaseHistory extends AbstractCreationAware<Employee> {
    private DiseaseHistoryId id;
    private Employee doctor;
    List<Diagnosis> diagnoses;
}
