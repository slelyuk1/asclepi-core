package com.jupiter.asclepi.core.web.controller.utils;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.restdocs.snippet.Attributes.key;

public class ConstraintDocumentationHelper {

    private static final String CONSTRAINTS_KEY = "constraints";
    private final ConstraintDescriptions descriptions;

    public static ConstraintDocumentationHelper of(Class<?> toAnalyze) {
        return new ConstraintDocumentationHelper(toAnalyze);
    }

    private ConstraintDocumentationHelper(Class<?> toAnalyze) {
        descriptions = new ConstraintDescriptions(toAnalyze);
    }

    public FieldDescriptor fieldDescriptorFor(String path) {
        List<String> constraints = descriptions.descriptionsForProperty(path);
        String descriptionConstraints = StringUtils.collectionToDelimitedString(constraints, ". ");
        return PayloadDocumentation.fieldWithPath(path).attributes(
                key(CONSTRAINTS_KEY).value(descriptionConstraints)
        );
    }
}
