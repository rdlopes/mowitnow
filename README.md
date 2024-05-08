# Mow It Now [![CICD](https://github.com/rdlopes/mowitnow/actions/workflows/cicd.yaml/badge.svg)](https://github.com/rdlopes/mowitnow/actions/workflows/cicd.yaml)

This project is offered as a response to the Xebia challenge called "Mow It Now".

It is built using Maven 3 and is written to be run on a JVM 1.6+.

## Compiling the project

Project uses Maven wrapper to compile, test and run.

The following commands can be invoked on the command line, starting at the root of the project:

* Compilation: ./mvn clean compile
* Unit testing: ./mvnw test
* Integration tests: ./mvnw verify

Please note that integration tests will generate the corresponding living documentation in the /docs folder.

## Running the project

Project will run after the following invocation:

```
./mvnw spring-boot:run -Dspring-boot.run.arguments=[path to the descriptions file]
```

where [path to the descriptions file] is the path from project root pointing at a lawn description.
For instance, if you wish to use the file included in this project describing the lawn setup as defined in the specs,
you would invoke

```
./mvnw spring-boot:run -Dspring-boot.run.arguments=src/main/resources/descriptions/lawn6x6-2mowers.txt
```

## Checking live documentation

Documentation on the tests passed can be accessed via the [github pages for this project](https://rdlopes.github.io/mowitnow/).
Please note that documentation is written in French.
