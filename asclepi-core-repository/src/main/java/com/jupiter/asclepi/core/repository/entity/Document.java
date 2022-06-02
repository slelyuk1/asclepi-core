package com.jupiter.asclepi.core.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigInteger;
import java.nio.file.Path;

// todo configure disease history when its functionality will be implemented
// todo configure analysis when its functionality will be implemented
// todo configure for persistence (Viktor Muratov) (see com.jupiter.asclepi.core.model.entity.people.Employee)
// todo configure validation (Viktor Muratov)
@Getter
@Setter
@ToString
public class Document {

    private BigInteger id;
    // todo write a converter like com.jupiter.asclepi.core.model.entity.converter.RoleConverter (Viktor Muratov)
    private Path path;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document document = (Document) o;
        return new EqualsBuilder()
                .append(getId(), document.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .toHashCode();
    }

}
