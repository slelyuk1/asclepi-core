package com.jupiter.asclepi.core.repository.entity;

import com.jupiter.asclepi.core.repository.entity.analysis.Analysis;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigInteger;
import java.nio.file.Path;

@Getter
@Setter
@ToString
@Entity
public class Document {

    @Id
    @GeneratedValue
    private BigInteger id;

    @Column(nullable = false)
    private Path path;

    private String description;

    @ManyToOne(optional = false)
    private Analysis analysis;

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
