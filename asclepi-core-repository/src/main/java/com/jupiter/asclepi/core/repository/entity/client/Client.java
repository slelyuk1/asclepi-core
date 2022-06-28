package com.jupiter.asclepi.core.repository.entity.client;

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
import java.math.BigInteger;

// todo think about persistable interface
@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Client implements CreationAware<String> {

    @Id
    @GeneratedValue
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

    @Embedded
    private CreationData<String> creation;

    public static Client fromId(BigInteger id) {
        Client toReturn = new Client();
        toReturn.setId(id);
        return toReturn;
    }

    public Client() {
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
        Client client = (Client) o;
        return new EqualsBuilder().append(getId(), client.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

}
