package com.example.library.catalogue.model;

import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SpringBootTest
class BookTypeValueObjectTests {

        private static final String BASE_PACKAGE = "com.example";
        private static final String DOMAIN_LAYER_PACKAGE_NAME = "model";

        private static final String DOMAIN_NAME = "Library";
        private static final String CONTEXT_NAME = "Catalogue";
        

        private static final String ENUM_NAME = "BookType";

        private static final String DOMAIN_PACKAGE_NAME = "library";
        private static final String CONTEXT_PACKAGE_NAME = "catalogue";
        

        private static final String DOMAIN_PACKAGE = "com.example.library";
        private static final String CONTEXT_PACKAGE = "com.example.library.catalogue.model";
        

        /**
         * There should be a type representing the 
         * The type should have the same name as the defined 
         * The type should reside in the domain layer
         * The type should reside in a package/module representing the related bounded
         * context
         */

        @Test
        void moduleShouldContainClassWithEnumsName() {
                // Import all classes from the base package
                JavaClasses importedClasses = new ClassFileImporter().importPackages(CONTEXT_PACKAGE);

                // Check if any class has the given name
                boolean classExists = importedClasses.stream()
                                .anyMatch(javaClass -> javaClass.getSimpleName().equals(ENUM_NAME));

                // Assert that the class exists
                assertThat(classExists)
                                .as("Check if a class named '%s' exists", ENUM_NAME)
                                .isTrue();
        }

        @Test
        void enumShouldResideInDomainLayer() {
                JavaClasses importedClasses = new ClassFileImporter().importPackages(CONTEXT_PACKAGE);

                JavaClass clazz = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(ENUM_NAME)).findFirst().get();

                ArchRule rule = classes().that().haveFullyQualifiedName(clazz.getFullName()).should()
                                .resideInAPackage(".." + DOMAIN_LAYER_PACKAGE_NAME + "..");

                rule.check(importedClasses);
        }

        @Test
        void enumShouldResideInBoundedContext() {
                JavaClasses importedClasses = new ClassFileImporter().importPackages(CONTEXT_PACKAGE);

                JavaClass clazz = importedClasses.stream()
                                .filter(javaClass -> javaClass.getSimpleName().equals(ENUM_NAME)).findFirst().get();

                ArchRule rule = classes().that().haveFullyQualifiedName(clazz.getFullName()).should()
                                .resideInAPackage(CONTEXT_PACKAGE + "..");

                rule.check(importedClasses);
        }

        
}
