package com.purple.cardealership;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;

@Profile("local")
public class CarDealershipRunner {

    private ConfigurableApplicationContext serviceApplicationContext;

    @PostConstruct
    public void startUp() {
        serviceApplicationContext = SpringApplication.run(CarDealershipApplication.class);
    }

    @PreDestroy
    public void shutDown() {
        serviceApplicationContext.close();
    }
}