package com.example.library;

import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class LibraryDomainTests {

    private static final String PACKAGE_STRUCTURE = "<base>.<domain>..<context>";
    private static final String BASE_PACKAGE = "com.example";

    private static final String DOMAIN_NAME = "Library";

    private static final String DOMAIN_PACKAGE_NAME = DOMAIN_NAME.toLowerCase();

    private static final String DOMAIN_PACKAGE = (PACKAGE_STRUCTURE.split("<domain>", 2)[0]
            + ".<domain>")
            .replace("<base>",
                    BASE_PACKAGE)
            .replace("<domain>",
                    DOMAIN_PACKAGE_NAME);

    /**
     * There should be a package/module representing the domain
     * The package/module should have the same name as the domain
     */

    @Test
    void domainPackageExists() {
        // Import all classes from the context package
        JavaClasses importedClasses = new ClassFileImporter().importPackages(DOMAIN_PACKAGE);

        // Assert loaded classes are greater than 0
        assertThat(importedClasses.size()).as("Check if classes exist in domain package '%s'", DOMAIN_PACKAGE)
                .isNotEqualTo(0);
    }
}
