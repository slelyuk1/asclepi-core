package com.jupiter.asclepi.core.model.entity.people;

import com.jupiter.asclepi.core.helper.object.api.AbstractCreationAware;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
public class Employee extends AbstractCreationAware<Employee> {
    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private Role role;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private String middleName;
    private String additionalInfo;
}
