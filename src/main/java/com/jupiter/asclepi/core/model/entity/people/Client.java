package com.jupiter.asclepi.core.model.entity.people;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.other.Job;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
public class Client extends AbstractCreationAware<Employee> {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private BigInteger id;

    @NotBlank
    @Column(name = "client_name")
    private String name;

    @NotBlank
    private String surname;

    private String middleName;

    @NotBlank
    private String residence;

    @NotNull
    private Boolean gender;

    private Job job;
}
