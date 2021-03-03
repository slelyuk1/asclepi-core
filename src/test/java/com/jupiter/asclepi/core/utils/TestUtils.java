package com.jupiter.asclepi.core.utils;

import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

public final class TestUtils {

    public static MockMvc createMockMvcDefaultConfiguration(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        return MockMvcBuilders.webAppContextSetup(context)
                .apply(
                        documentationConfiguration(restDocumentation)
                                .operationPreprocessors().withRequestDefaults(prettyPrint()).withResponseDefaults(prettyPrint())
                ).build();
    }

    private TestUtils() {
        throw new IllegalStateException("TestUtils class mustn't be instantiated!");
    }
}
