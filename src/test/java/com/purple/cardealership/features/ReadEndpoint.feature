Feature: Testing read endpoint
  Scenario: Reading a car with no parameters
    Given that we already have a car
    When the get request has no params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing the car

  Scenario: Reading a car filtering by brand
    Given that we already have a car
    When the get request has brand params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing the car

  Scenario: Reading a car filtering by engineSize
    Given that we already have a car
    When the get request has engineSize params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing the car

  Scenario: Reading a car filtering by mileage
    Given that we already have a car
    When the get request has mileage params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing the car

  Scenario: Reading a car filtering by age
    Given that we already have a car
    When the get request has age params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing the car

  Scenario: Reading a car filtering by engineSize, returning nothing
    Given that we already have a car
    When the get request has engineSizeFail params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing nothing

