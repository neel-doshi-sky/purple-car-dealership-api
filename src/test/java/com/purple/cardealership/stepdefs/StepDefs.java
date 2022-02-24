package com.purple.cardealership.stepdefs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

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

@CucumberContextConfiguration
@SpringBootTest
public class StepDefs {
    HttpResponse response;
    String jsonString = "{}";
    String body = "";
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String getParams = "";

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
            case "no":
                getParams = "";
                break;
            case "brand":
                getParams = "?brand=asd";
                break;
            case "age":
                getParams = "?ageStart=100&ageStop=200";
                break;
            case "mileage":
                getParams = "?mileageStart=123455&ageStop=123457";
                break;
            case "engineSize":
                getParams = "?engineSizeStart=1.3&engineSizeStop=1.5";
                break;
            case "engineSizeFail":
                getParams = "?engineSizeStart=1.5&engineSizeStop=1.6";
                break;
            default:
                getParams = "";
                break;
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

    @And("a {string} of {string}")
    public void the_user_gets_a_body_containing_key_value_pair(String key, String value) {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        assertEquals(value, jsonBody.get(key).getAsString());
    }

    @And("a resultSet containing car 1")
    public void the_user_gets_results_of_cars() {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        JsonObject result = jsonBody.get("results").getAsJsonArray().get(0).getAsJsonObject();
        JsonObject expectedResult = JsonParser.parseString(
                "{\"id\": 1, \"brand\":\"asd\",\"model\":\"asd2\",\"age\":123,\"mileage\":123456,\"engineSize\":1.4}")
                .getAsJsonObject();
        assertEquals(expectedResult, result);
    }

    @And("a resultSet containing nothing")
    public void the_user_gets_no_results() {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
        int result = jsonBody.get("results").getAsJsonArray().size();
        assertEquals(0, result);
    }

}
