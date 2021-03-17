package com.jupiter.asclepi.core.model.entity.disease;

import com.jupiter.asclepi.core.helper.api.object.AbstractCreationAware;
import com.jupiter.asclepi.core.model.entity.disease.visit.Visit;
import com.jupiter.asclepi.core.model.entity.document.Document;
import com.jupiter.asclepi.core.model.entity.people.Employee;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class Analysis extends AbstractCreationAware<Employee> {
    private BigInteger id;
    private Visit visit;
    private String summary;
    private List<Document> documents;
}
