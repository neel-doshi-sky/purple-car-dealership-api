package com.purple.cardealership.stepdefs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import wiremock.org.apache.http.HttpResponse;
import wiremock.org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.client.methods.HttpPost;
import wiremock.org.apache.http.client.methods.HttpPut;
import wiremock.org.apache.http.entity.StringEntity;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;
import wiremock.org.apache.http.util.EntityUtils;

@CucumberContextConfiguration
// @SpringBootTest
@DataJpaTest
public class StepDefs {
    HttpResponse response;
    String jsonString = "{}";
    String body = "";
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String getParams = "";
    Long currentCarId = 0L;

    @Given("that we already have a car")
    public void makeCar() throws Throwable {
        the_user_enters_invalid_or_valid_data("valid");
        the_user_calls_create();
    }

    @When("the body contains {string} data")
    public void the_user_enters_invalid_or_valid_data(String status) {
        if (Objects.equals(status, "valid")) {
            jsonString = "{\"brand\":\"asd\",\"model\":\"asd2\",\"age\":\"123\",\"mileage\":\"123456\",\"engineSize\":\"1.4\"}";
        } else {
            jsonString = "{\"model\":\"asd2\",\"age\":\"123\",\"mileage\":\"123456\",\"engineSize\":\"1.4\"}";
        }
    }

    @When("the get request has {word} params")
    public void set_get_params(String paramTypes) {
        switch (paramTypes) {
            case "no" -> getParams = "";
            case "brand" -> getParams = "?brand=asd";
            case "age" -> getParams = "?ageStart=100&ageStop=200";
            case "mileage" -> getParams = "?mileageStart=123455&ageStop=123457";
            case "engineSize" -> getParams = "?engineSizeStart=1.3&engineSizeStop=1.5";
            case "engineSizeFail" -> getParams = "?engineSizeStart=1.5&engineSizeStop=1.6";
            default -> getParams = "";
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
        try {
            currentCarId = Long
                    .parseLong(
                            JsonParser.parseString(body).getAsJsonObject().get("message").getAsString().split(":")[1]);
        } catch (Exception e) {
            currentCarId = 0L;
        }

    }

    @And("the user calls the get endpoint")
    public void the_user_calls_get() throws Throwable {
        HttpGet request = new HttpGet("http://localhost:8080/car/read" + getParams);
        response = httpClient.execute(request);
        body = EntityUtils.toString(response.getEntity(), "UTF-8");

    }

    @Then("the user should get a response status code of {int}")
    public void the_user_gets_a_response_code(int code) {
        assertEquals(code, response.getStatusLine().getStatusCode());
    }

    @And("a 201 of the car was created with id:")
    public void the_user_gets_a_body_containing_201_conf() {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        assertEquals("The car was created with id:" + currentCarId, jsonBody.get("message").getAsString());
    }

    @And("a update message")
    public void update_message() {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        assertEquals("Updated car with ID: " + currentCarId, jsonBody.get("message").getAsString());
    }

    @And("a {string} of {string}")
    public void the_user_gets_a_body_containing_key_value_pair(String key, String value) {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        assertEquals(value, jsonBody.get(key).getAsString());
    }

    @And("a resultSet containing the car")
    public void the_user_gets_results_of_cars() {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        JsonObject result = null;
        for (JsonElement e : jsonBody.get("results").getAsJsonArray()) {
            if (e.getAsJsonObject().get("id").getAsLong() == currentCarId) {
                result = e.getAsJsonObject();
            }
        }
        JsonObject expectedResult = JsonParser.parseString(
                "{\"id\": " + currentCarId
                        + ", \"brand\":\"asd\",\"model\":\"asd2\",\"age\":123,\"mileage\":123456,\"engineSize\":1.4}")
                .getAsJsonObject();
        assertEquals(expectedResult, result);
    }

    @And("a result equal to car 1")
    public void the_user_gets_result_of_car() {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        JsonObject result = jsonBody.get("results").getAsJsonObject();
        JsonObject expectedResult = JsonParser.parseString(
                "{\"id\": " + currentCarId
                        + ", \"brand\":\"asd\",\"model\":\"asd2\",\"age\":123,\"mileage\":123456,\"engineSize\":1.4}")
                .getAsJsonObject();
        assertEquals(expectedResult, result);
    }

    @And("a resultSet containing nothing")
    public void the_user_gets_no_results() {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        int result = jsonBody.get("results").getAsJsonArray().size();
        assertEquals(0, result);
    }

    @When("the put request has an id parameter that exists")
    public void the_user_passes_in_id_to_update_existing_car() {
        jsonString = "{\"id\": \"" + currentCarId + "\",\"brand\":\"asd\"}";
    }

    @And("the user calls the update endpoint")
    public void the_user_calls_put_endpoint() throws Throwable {
        HttpPut request = new HttpPut("http://localhost:8080/car/update");
        StringEntity entity = new StringEntity(jsonString);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        response = httpClient.execute(request);
        body = EntityUtils.toString(response.getEntity(), "UTF-8");
    }

}
