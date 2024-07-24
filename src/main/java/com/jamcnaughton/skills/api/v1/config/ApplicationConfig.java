package com.jamcnaughton.skills.api.v1.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Manages the application's configuration. */
@Configuration
@EnableTransactionManagement
@EntityScan("com.jamcnaughton.skills.model.entity")
public class ApplicationConfig {}
