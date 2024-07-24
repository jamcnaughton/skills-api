package com.jamcnaughton.skills.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/** Class for defining and running the Spring application. */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Skills API", version = "1.0", description = "API for Skills"))
@PropertySource(value = "classpath:swagger.properties", encoding = "UTF-8")
public class SkillsApiApplication {

  /**
   * Runs the API.
   *
   * @param args Arguments to supply to the API.
   */
  public static void main(String[] args) {
    SpringApplication.run(SkillsApiApplication.class, args);
  }

  /**
   * Bean for converting between entities and DTOs.
   *
   * @return The model mapping bean.
   */
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
