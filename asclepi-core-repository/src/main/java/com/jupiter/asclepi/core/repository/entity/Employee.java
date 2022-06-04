package com.jupiter.asclepi.core.repository.entity;

import com.jupiter.asclepi.core.repository.helper.api.AbstractCreationAware;
import com.jupiter.asclepi.core.repository.entity.other.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
public class Employee extends AbstractCreationAware<Employee> {

    @Id
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String login;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private String middleName;

    private String additionalInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return new EqualsBuilder().append(getId(), employee.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
