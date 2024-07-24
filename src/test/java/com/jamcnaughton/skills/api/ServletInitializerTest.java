package com.jamcnaughton.skills.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.builder.SpringApplicationBuilder;

/** Servlet initializer tests. */
public class ServletInitializerTest {

  /** Test initialize servlet. */
  @Test
  @DisplayName("Initialize Servlet")
  void testInitializeServlet() {
    SpringApplicationBuilder springApplicationBuilder =
        Mockito.mock(SpringApplicationBuilder.class);
    ServletInitializer servletInitializer = new ServletInitializer();
    when(springApplicationBuilder.sources(SkillsApiApplication.class))
        .thenReturn(springApplicationBuilder);

    // When
    SpringApplicationBuilder builder = servletInitializer.configure(springApplicationBuilder);

    // Then
    assertEquals(springApplicationBuilder, builder);
  }
}
