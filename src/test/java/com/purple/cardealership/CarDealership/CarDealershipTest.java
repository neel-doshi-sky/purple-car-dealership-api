package com.purple.cardealership.CarDealership;

import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/purple/cardealership/CarDealership")
public class CarDealershipTest {

    @When("^the user calls the create endpoint")
    public void the_user_calls_create() throws Throwable{

        WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());

    }


}