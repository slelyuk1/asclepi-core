package com.jupiter.asclepi.core.model.entity.people;

import com.jupiter.asclepi.core.helper.object.api.AbstractCreationAware;
import lombok.Data;

@Data
public class Client extends AbstractCreationAware<Employee> {
    private Integer id;
    private String name;
    private String surname;
    private String middleName;
    private String residence;
    // can be more than two ??? hehehe
    private Boolean gender;
    private Job job;
}
