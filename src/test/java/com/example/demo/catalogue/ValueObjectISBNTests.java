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
class ValueObjectISBNTests {
        // Define the base package where your classes are located
        private static final String BASE_PACKAGE = "com.example.demo.catalogue";

        private static final String VALUEOBJECT_NAME = "ISBN";

        private static final String DOMAIN_LAYER = "..model..";
        private static final String BOUNDED_CONTEXT = "..catalogue..";

        @Test
        void moduleShouldContainClassWithEntitiesName() {
                // Import all classes from the base package
                JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

                // Check if any class has the given name
                boolean classExists = importedClasses.stream()
                                .anyMatch(javaClass -> javaClass.getSimpleName().equals(VALUEOBJECT_NAME));

                // Assert that the class exists
                assertThat(classExists)
                                .as("Check if a class named '%s' exists", VALUEOBJECT_NAME)
                                .isTrue();
        }

        @Test
        void entityShouldResideInDomainLayer() {
                JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

                JavaClass entityClass = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(VALUEOBJECT_NAME)).findFirst()
                                .get();

                ArchRule rule = classes().that().haveFullyQualifiedName(entityClass.getFullName()).should()
                                .resideInAPackage(DOMAIN_LAYER);

                rule.check(importedClasses);
        }

        @Test
        void entityShouldResideInBoundedContext() {
                JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

                JavaClass entityClass = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(VALUEOBJECT_NAME)).findFirst()
                                .get();

                ArchRule rule = classes().that().haveFullyQualifiedName(entityClass.getFullName()).should()
                                .resideInAPackage(BOUNDED_CONTEXT);

                rule.check(importedClasses);
        }
}
