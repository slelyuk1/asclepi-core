package com.jupiter.asclepi.core.repository.entity.employee;

import com.jupiter.asclepi.core.repository.helper.api.CreationAware;
import com.jupiter.asclepi.core.repository.helper.api.CreationData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Employee implements CreationAware<String> {

    @Id
    @GeneratedValue
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

    private CreationData<String> creation;

    public Employee() {
        creation = new CreationData<>();
    }

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
