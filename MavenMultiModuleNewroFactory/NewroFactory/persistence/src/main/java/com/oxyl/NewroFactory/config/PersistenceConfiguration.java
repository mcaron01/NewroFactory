package com.oxyl.NewroFactory.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.oxyl.NewroFactory.persistence"})
public class PersistenceConfiguration {
}
