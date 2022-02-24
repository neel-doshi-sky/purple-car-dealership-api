package com.purple.cardealership.CarDealership;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import wiremock.org.apache.http.HttpResponse;
import wiremock.org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.client.methods.HttpPost;
import wiremock.org.apache.http.entity.StringEntity;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;
import wiremock.org.apache.http.util.EntityUtils;

import java.util.Objects;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberSpringConfiguration {
    HttpResponse response;
    String jsonString = "{}";
    String body = "";
    CloseableHttpClient httpClient = HttpClients.createDefault();

    @When("the body contains {string} data")
    public void the_user_enters_invalid_or_valid_data(String status){
        if(Objects.equals(status, "valid")){
            jsonString = "{\"brand\":\"asd\",\"model\":\"asd2\",\"age\":\"123\",\"mileage\":\"123456\",\"engineSize\":\"1.4\"}";
        } else {
            jsonString = "{\"model\":\"asd2\",\"age\":\"123\",\"mileage\":\"123456\",\"engineSize\":\"1.4\"}";
        }

    }

    @And("the user calls the create endpoint")
    public void the_user_calls_create() throws Throwable {

        HttpPost request = new HttpPost("http://localhost:8080/car/create");
        StringEntity entity = new StringEntity(jsonString);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        response = httpClient.execute(request);
        body = EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    @Then("the user should get a response status code of {int}")
    public void the_user_gets_a_response_code(int code){
        assertEquals(code, response.getStatusLine().getStatusCode());
    }

    @And("a {string} of {string}")
    public void the_user_gets_a_body_containing_key_value_pair(String key, String value){
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        assertEquals(value, jsonBody.get(key).getAsString());
    }

    @And("a results of cars")
    public void the_user_gets_results_of_cars(){
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        String result = jsonBody.get("results").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
        assertEquals("1",result);
    }


    @And("the user calls the get endpoint")
    public void the_user_calls_get() throws Throwable {
        HttpGet request =  new HttpGet("http://localhost:8080/car/read");
        response = httpClient.execute(request);
        body = EntityUtils.toString(response.getEntity(), "UTF-8");

    }




}
