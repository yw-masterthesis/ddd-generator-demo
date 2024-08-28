package com.example.library.catalogue;

import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SpringBootTest
class EntityBookTests {
        private static final String PACKAGE_STRUCTURE = "<base>.<domain>..<context>";
        private static final String BASE_PACKAGE = "com.example";
        private static final String DOMAIN_LAYER_PACKAGE_NAME = "model";

        private static final String DOMAIN_NAME = "Library";
        private static final String CONTEXT_NAME = "Catalogue";
        private static final String AGGREGATE_NAME = "Book";
        private static final String ENTITY_NAME = "Book";

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

        /**
         * There should be a type representing the entity
         * The type should have the same name as the defined entity
         * The type should reside in the domain layer
         * The type should reside in a package/module representing the related bounded
         * context
         */

        @Test
        void moduleShouldContainClassWithEntitiesName() {
                // Import all classes from the base package
                JavaClasses importedClasses = new ClassFileImporter().importPackages(CONTEXT_PACKAGE);

                // Check if any class has the given name
                boolean classExists = importedClasses.stream()
                                .anyMatch(javaClass -> javaClass.getSimpleName().equals(ENTITY_NAME));

                // Assert that the class exists
                assertThat(classExists)
                                .as("Check if a class named '%s' exists", ENTITY_NAME)
                                .isTrue();
        }

        @Test
        void entityShouldResideInDomainLayer() {
                JavaClasses importedClasses = new ClassFileImporter().importPackages(CONTEXT_PACKAGE);

                JavaClass entityClass = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(ENTITY_NAME)).findFirst().get();

                ArchRule rule = classes().that().haveFullyQualifiedName(entityClass.getFullName()).should()
                                .resideInAPackage(".." + DOMAIN_LAYER_PACKAGE_NAME + "..");

                rule.check(importedClasses);
        }

        @Test
        void entityShouldResideInBoundedContext() {
                JavaClasses importedClasses = new ClassFileImporter().importPackages(CONTEXT_PACKAGE);

                JavaClass entityClass = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(ENTITY_NAME)).findFirst().get();

                ArchRule rule = classes().that().haveFullyQualifiedName(entityClass.getFullName()).should()
                                .resideInAPackage(CONTEXT_PACKAGE + "..");

                rule.check(importedClasses);
        }
}
