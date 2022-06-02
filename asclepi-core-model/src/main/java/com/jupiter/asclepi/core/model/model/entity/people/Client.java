package com.jupiter.asclepi.core.model.model.entity.people;

import com.jupiter.asclepi.core.model.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.model.other.Job;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@Entity
public class Client extends AbstractCreationAware<Employee> {

    @Id
    private BigInteger id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private String middleName;

    @NotBlank
    private String residence;

    // todo verify all notnull annotations
    @NotNull
    private Boolean gender;

    @Embedded
    private Job job;

    public static Client fromId(BigInteger id) {
        Client toReturn = new Client();
        toReturn.setId(id);
        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return new EqualsBuilder().append(getId(), client.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
