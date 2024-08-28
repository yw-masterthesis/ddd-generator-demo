package com.example.library.lending;

import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SpringBootTest
class EntityAvailableBookTests {
        // Define the base package where your classes are located
        private static final String BASE_PACKAGE = "com.example.demo.lending";

        private static final String ENTITY_NAME = "Book";
        private static final String IDENTITY_NAME = "BookId";

        private static final String DOMAIN_LAYER = "..model..";
        private static final String BOUNDED_CONTEXT = "..lending..";

        @Test
        void moduleShouldContainClassWithEntitiesName() {
                // Import all classes from the base package
                JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

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
                JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

                JavaClass entityClass = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(ENTITY_NAME)).findFirst().get();

                ArchRule rule = classes().that().haveFullyQualifiedName(entityClass.getFullName()).should()
                                .resideInAPackage(DOMAIN_LAYER);

                rule.check(importedClasses);
        }

        @Test
        void entityShouldResideInBoundedContext() {
                JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

                JavaClass entityClass = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(ENTITY_NAME)).findFirst().get();

                ArchRule rule = classes().that().haveFullyQualifiedName(entityClass.getFullName()).should()
                                .resideInAPackage(BOUNDED_CONTEXT);

                rule.check(importedClasses);
        }

        @Test
        void entityShouldHaveIdentity() throws ClassNotFoundException {
                JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

                JavaClass entityClass = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(ENTITY_NAME)).findFirst().get();
                JavaClass identityClass = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(IDENTITY_NAME)).findFirst().get();

                ArchRule rule = classes().that().haveFullyQualifiedName(entityClass.getFullName()).should()
                                .dependOnClassesThat().haveFullyQualifiedName(identityClass.getFullName());

                rule.check(importedClasses);

                // Check if the class has a field of the specified type
                boolean hasFieldOfType = entityClass.getFields().stream()
                                .anyMatch(field -> field.getRawType().isAssignableTo(identityClass.getFullName()));

                // Assert that the field of the specified type exists
                assertThat(hasFieldOfType)
                                .as("Entity '%s' should have a field of type '%s'", ENTITY_NAME,
                                                IDENTITY_NAME)
                                .isTrue();
        }
}
