# openapi-spring-test-generator

A Spring Boot starter that generates OpenAPI documentation during the test phase of the project.

This library wraps [springdoc-openapi](https://github.com/springdoc/springdoc-openapi) library that produces OpenAPI endpoint in running Spring applications.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/eu.lundegaard.java/openapi-spring-test-generator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/eu.lundegaard.java/git-checkout-plugin)

# POM Configuration 
Add following dependency with test scope to activate this library. For webflux applications, use `openapi-spring-test-generator-weblux` artifact.

```xml
<project>
  ...
  <dependencies>
    ...
    <dependency>
        <groupId>eu.lundegaard.java</groupId>
        <artifactId>openapi-spring-test-generator-mvc</artifactId>
        <version>${latest-version}</version>
        <scope>test</scope>
    </dependency>
    ...
  </dependencies>
   ...
</project>

```

# Configuration
You can configure output by passing arguments via the surefire plugin.

```xml
<project>
    ...
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <systemPropertyVariables>
                        <openpi-generator.generateJson>false</openpi-generator.generateJson>
                        <openpi-generator.generateYaml>true</openpi-generator.generateYaml>
                        <openpi-generator.outputDirectory>target/generated-docs</openpi-generator.outputDirectory>
                    </systemPropertyVariables>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

|Name|Default Value|Description|
|----|-------------|-----------|
|`openpi-generator.generateJson`|true|If true openapi.json will be generated into the output folder|
|`openpi-generator.generateYaml`|true|If true openapi.yaml will be generated into the output folder|
|`openpi-generator.outputDirectory`|`target/classes/static/docs`|Output folder|