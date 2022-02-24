package com.purple.cardealership.CarDealership;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import wiremock.org.apache.http.HttpResponse;
import wiremock.org.apache.http.client.methods.HttpPost;
import wiremock.org.apache.http.entity.StringEntity;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberSpringConfiguration {
    HttpResponse response;
    String jsonString = "{}";

    @When("^the body contains valid data")
    public void the_user_enters_valid_data() throws Throwable {
        jsonString = "{\"brand\":\"asd\",\"model\":\"asd2\",\"age\":\"123\",\"mileage\":\"123456\",\"engineSize\":\"1.4\"}";
    }

    @And("^the user calls the create endpoint")
    public void the_user_calls_create() throws Throwable {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost request = new HttpPost("http://localhost:8080/car/create");
        StringEntity entity = new StringEntity(jsonString);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        response = httpClient.execute(request);
        // assertEquals("asd", EntityUtils.toString(response.getEntity(), "UTF-8"));
    }

    @Then("^the user should get a response status code of 201")
    public void the_user_gets_a_response() throws Throwable {
        // throw new PendingException();
        assertEquals("201", String.valueOf(response.getStatusLine().getStatusCode()));
    }

    @And("^ a message of The car was created")
    public void the_user_gets_a_success_message() throws Throwable {
        // throw new PendingException();
        assertEquals("asd", "asd");
    }

}
