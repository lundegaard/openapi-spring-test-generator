/*
 * Copyright Lundegaard a.s., 2019
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.lundegaard.java.ostg.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.lundegaard.java.ostg.common.config.GeneratorProperties;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.api.AbstractOpenApiResource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Lukas Zaruba, lukas.zaruba@lundegaard.eu, 2019
 */
public abstract class OpenApiGenerator {

    @Autowired
    private GeneratorProperties properties;

    abstract protected AbstractOpenApiResource getOpenAPIResource();

    @PostConstruct
    public void initialize() {
        OpenAPI openAPI = getOpenAPI();
        try {
            if (properties.isGenerateJson()) {
                writeFile(Json.pretty().writeValueAsString(openAPI), "openapi.json");
            }
            if (properties.isGenerateYaml()) {
                writeFile(Yaml.pretty().writeValueAsString(openAPI), "openapi.yaml");
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error while converting openAPI to json or yaml", e);
        }
    }

    private OpenAPI getOpenAPI() {
        try {
            Method getOpenApi = AbstractOpenApiResource.class.getDeclaredMethod("getOpenApi");
            getOpenApi.setAccessible(true);
            return (OpenAPI) getOpenApi.invoke(getOpenAPIResource());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Error while invoking getOpenApi", e);
        }
    }

    private void writeFile(String content, String fileName) {
        try {
            Path dir = Paths.get(properties.getOutputDirectory());
            Files.createDirectories(dir);
            Files.write(dir.resolve(fileName), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write file " + fileName, e);
        }
    }

}
