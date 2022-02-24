package com.purple.cardealership.CarDealership;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/purple/cardealership/CarDealership/", glue = "src/test/java/com/purple/cardealership/CarDealership/")
public class CarDealershipTest {
}