package com.jupiter.asclepi.core.repository.entity.employee;

import com.jupiter.asclepi.core.repository.converter.RoleAttributeConverter;
import com.jupiter.asclepi.core.repository.helper.api.CreationAware;
import com.jupiter.asclepi.core.repository.helper.api.CreationData;
import com.jupiter.asclepi.core.repository.helper.api.CustomPersistable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Employee implements CustomPersistable<Integer>, CreationAware<String> {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Convert(converter = RoleAttributeConverter.class)
    private Role role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
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
