package com.purple.cardealership.CarDealership;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/purple/cardealership/CarDealership/")
public class CarDealershipTest {

}