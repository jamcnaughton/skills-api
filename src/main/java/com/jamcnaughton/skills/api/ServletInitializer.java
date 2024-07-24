package com.jamcnaughton.skills.api;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/** Helper class for initialising the application's server. */
public class ServletInitializer extends SpringBootServletInitializer {

  /**
   * Configures a spring application for running as a servlet.
   *
   * @param application The application to configure
   * @return The configured application.
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SkillsApiApplication.class);
  }
}
