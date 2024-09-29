# DDD Domain Model Test Generator Demo

This project is part of my master thesis "Bridging the Gap Between Specification and Implementation in DDD" at TH Köln University of Applied Sciences.

The **DDD Domain Model Test Generator Demo** is a demonstration of the implemented [DDD Domain Model Test Generator](https://github.com/yw-masterthesis/ddd-generator), a Yeoman ([https://yeoman.io](https://yeoman.io/)) code generator capable of generating domain model tests for Java Spring Applications.

Domain model tests introduced in the thesis are tests validating the model-code gap based on a properly described domain model. In my work a DDD metamodel were defined to describe a domain model with patterns from Domain-Driven Design (DDD).

## Installation

Before using the **DDD Domain Model Test Generator** you need to install the yeoman cli and the generator. You can install Yeoman globally using npm:

```sh
npm install -g yo
```

At the current state the **DDD Domain Model Test Generator** is not publicly available as a npm package. Therefore, you need to clone the repository and install the package manually.

```sh
git clone https://github.com/yw-masterthesis/ddd-generator.git
cd ddd-generator
npm install
npm run build
npm link
```

## Usage

To use the **DDD Domain Model Test Generator** you need to define your domain model with the DDD Metamodel described in the thesis and save it as XML file.

For this demo you can find a described domain model in the root folder [domain-model.xml](./domain-model.xml). The domain model describes the Catalogue context from the domain of a public library. The domain model is inspired by the [DDD by Examples Library Project](https://github.com/ddd-by-examples/library)

Furthermore you require a [generator-config.json](./generator-config.json) also given in the root folder to provide the code generator with additional information about your project structure. The generator-config.json contains details about your project to generate working tests. This contains the basePath of your package, e.g. ```com.example```. The package descriptor for your domain logic when following the clean architecture pattern, e.g. ```model``` or ```domain```. And a string describing package hirarchy of your project, e.g. ```<base>.<domain>.<context>.<layer>.<aggregate>```. The placeholder can be rearanged based on your hirarchy.

To execute the code generation you need to excecute the DDD Domain Model Test Generator with the Yeoman CLI in the root of your Java Spring Boot Application:

```sh
yo ddd
```

The generator will prompt you about the required files and an output directory. For this demo you can answer all question with their default value. After this the generator will generate the domain model tests files based on the described domain model.

An example of a generated test looks like this:

```Java
package com.example.library.catalogue.model.books;

...

@SpringBootTest
class BookValueObjectTests {

        private static final String DOMAIN_LAYER_PACKAGE_NAME = "model";
        private static final String ENTITY_NAME = "Book";
        private static final String CONTEXT_PACKAGE = "com.example.library.catalogue.model";
        private static final String AGGREGATE_PACKAGE = "com.example.library.catalogue.model.books";

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
...
```


The generation process can be rerun if necessary. E.g. after you extended or changed the domain model. The generator will ask to overwrite the existing tests based on the new domain model.
