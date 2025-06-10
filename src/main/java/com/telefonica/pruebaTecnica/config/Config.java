package com.telefonica.pruebaTecnica.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class Config {
	@Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
