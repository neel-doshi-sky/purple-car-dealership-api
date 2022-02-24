package com.purple.cardealership;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/purple/cardealership/features/", glue = "src/test/java/com/purple/cardealership/stepdefs/")
public class CarDealershipTest {
}