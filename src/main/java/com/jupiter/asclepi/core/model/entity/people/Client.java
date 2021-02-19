package com.jupiter.asclepi.core.model.entity.people;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.other.Job;
import lombok.Data;
import lombok.EqualsAndHashCode;

// todo configure for persistence (Dima Kuzmenko) (see com.jupiter.asclepi.core.model.entity.people.Employee)
// todo configure validation (Dima Kuzmenko)
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Client extends AbstractCreationAware<Employee> {
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String surname;
    private String middleName;
    private String residence;
    private Boolean gender;
    private Job job;
}
