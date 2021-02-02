package com.jupiter.asclepi.core.model.request.people;

import com.jupiter.asclepi.core.model.entity.people.Role;
import lombok.Data;

@Data
public class EditEmployeeRequest {
    private String login;
    private String password;
    // todo good idea to use entity in request
    private Role role;
    private String name;
    private String surname;
    private String middleName;
    private String additionalInfo;
}
