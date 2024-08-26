package com.example.demo.catalogue;

import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class CatalogueContextTests {
    // Bounded Context Info
    private static final String BOUNDED_CONTEXT_NAME = "Catalogue";
    private static final String[] UPSTREAM_CONTEXTS = {};

    // Project and packages
    private static final String BASE_PACKAGE = "com.example.demo.catalogue";
    private static final String BOUNDED_CONTEXT_LAYER = "..catalogue..";
    private static final String DOMAIN_LAYER = "..model..";
    private static final String[] WHITELIST_PACKAGES = { BOUNDED_CONTEXT_LAYER, "java..", "lombok.." };

    @Test
    void contextShouldOnlyDependOnWhitelistedPackages() {
        // Import all classes from the base package
        JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

        ArchRule rule = classes().that().resideInAPackage(BOUNDED_CONTEXT_LAYER).and().resideInAPackage(DOMAIN_LAYER)
                .should().onlyDependOnClassesThat().resideInAnyPackage(WHITELIST_PACKAGES);

        // Assert that the class exists
        rule.check(importedClasses);
    }
}
