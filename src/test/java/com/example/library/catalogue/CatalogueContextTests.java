package com.example.library.catalogue;

import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class CatalogueContextTests {

    private static final String PACKAGE_STRUCTURE = "<base>.<domain>..<context>";
    private static final String BASE_PACKAGE = "com.example";
    private static final String DOMAIN_LAYER_PACKAGE_NAME = "model";

    private static final String DOMAIN_NAME = "Library";
    private static final String CONTEXT_NAME = "Catalogue";

    private static final String DOMAIN_PACKAGE_NAME = DOMAIN_NAME.toLowerCase();
    private static final String CONTEXT_PACKAGE_NAME = CONTEXT_NAME.toLowerCase();

    private static final String DOMAIN_PACKAGE = (PACKAGE_STRUCTURE.split("<domain>", 2)[0]
            + ".<domain>")
            .replace("<base>",
                    BASE_PACKAGE)
            .replace("<domain>",
                    DOMAIN_PACKAGE_NAME);
    private static final String CONTEXT_PACKAGE = (PACKAGE_STRUCTURE.split("<context>", 2)[0] + "<context>")
            .replace("<base>",
                    BASE_PACKAGE)
            .replace("<domain>",
                    DOMAIN_PACKAGE_NAME)
            .replace("<layer>",
                    DOMAIN_LAYER_PACKAGE_NAME)
            .replace("<context>",
                    CONTEXT_PACKAGE_NAME);

    private static final String[] WHITELIST_PACKAGES = { CONTEXT_PACKAGE, "java..", "lombok.." };

    /**
     * There should be a package/module representing the bounded context
     * The package/module should have the same name as the bounded context
     * The package should only have dependencies to Upstream Contexts, OHS, SK
     * The package should have a dependency to defined upstream contexts
     */

    @Test
    void contextPackageExists() {
        // Import all classes from the context package
        JavaClasses importedClasses = new ClassFileImporter().importPackages(CONTEXT_PACKAGE);

        // Assert loaded classes are greater than 0
        assertThat(importedClasses.size()).as("Check if classes exist in context package '%s'", CONTEXT_PACKAGE)
                .isNotEqualTo(0);
    }

    @Test
    void contextPackageResideInDomainPackage() {
        // Import all classes from the context package
        JavaClasses importedClasses = new ClassFileImporter().importPackages(DOMAIN_PACKAGE);

        // Assert loaded classes are greater than 0
        assertThat(importedClasses.size()).as("Check if classes exist in context package '%s'", CONTEXT_PACKAGE)
                .isNotEqualTo(0);
    }

    // @Test
    // void contextPackageExists() {
    // // Import all classes from the context package
    // JavaClasses importedClasses = new
    // ClassFileImporter().importPackages(DOMAIN_PACKAGE);

    // ArchRule rule =
    // classes().that().resideInAPackage(BOUNDED_CONTEXT_LAYER).and().resideInAPackage(DOMAIN_LAYER)
    // .should().onlyDependOnClassesThat().resideInAnyPackage(WHITELIST_PACKAGES);

    // // Assert that the class exists
    // rule.check(importedClasses);
    // }
}
