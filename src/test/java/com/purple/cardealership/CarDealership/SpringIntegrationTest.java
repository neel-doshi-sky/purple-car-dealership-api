package com.purple.cardealership.CarDealership;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest
public class SpringIntegrationTest {
    @When("^the user calls the create endpoint")
    public void the_user_calls_create() throws Throwable{
        String jsonString = "{}";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost request = new HttpPost("http://localhost:8080/car/create");
        StringEntity entity = new StringEntity(jsonString);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);

    }

    @And("^the body contains valid data")
    public void the_user_enters_valid_data() throws Throwable {
        throw new PendingException();
    }

    @Then("^the user should get a response status code of 200")
    public void the_user_gets_a_response()throws Throwable {
        throw new PendingException();
    }

    @And("^ a message of The car was created")
    public void the_user_gets_a_success_message()throws Throwable {
        throw new PendingException();
    }

}
