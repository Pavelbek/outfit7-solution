# Expertise Test solution for Outfit7

### Requirements

* JDK 8+
* Maven 3.6+
* Install [Lombok plugin for IntelliJ IDEA](https://plugins.jetbrains.com/plugin/6317-lombok-plugin)

### Technologies used

* [JUnit5](https://junit.org/junit5/docs/current/user-guide/) as test runner
* [Spring boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) - for DI and just for fun, actually there is no need in it
* [Allure Framework](https://docs.qameta.io/allure) as test report tool
* [Lombok](https://projectlombok.org/) to get rid of boilerplate code

### Structure

* client package contains the lowest layer when we just make calls to some endpoints
* steps layer should be used for some more complex logical operations - don't need for now
* endpoints package keeps all the endpoints
* dto - models for deserialized payloads
* util is util
* config - here we can create, link together and configure our beans using java code
* TaskApplication class creates context
* @SpringBootTest - this annotation in test classes builds up context for tests

### How to run tests locally

* Just run command: ```mvn clean test```

### How to generate test report

* Use command in your terminal inside your project folder ```mvn allure:serve```