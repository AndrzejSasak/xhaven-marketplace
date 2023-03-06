package com.xhaven.xhavenserver.config.persistance;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.xhaven.xhavenserver.repository")
public class PersistenceConfig {

}
