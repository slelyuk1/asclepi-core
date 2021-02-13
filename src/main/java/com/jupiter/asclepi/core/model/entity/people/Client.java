package com.jupiter.asclepi.core.model.entity.people;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.other.Job;
import lombok.Data;

@Data
public class Client extends AbstractCreationAware<Employee> {
    private Integer id;
    private String name;
    private String surname;
    private String middleName;
    private String residence;
    private Boolean gender;
    private Job job;
}
