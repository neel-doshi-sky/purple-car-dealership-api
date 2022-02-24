Feature: Testing read endpoint
  Scenario: Reading a car with no parameters
    When the get request has no params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing car 1

  Scenario: Reading a car filtering by brand
    When the get request has brand params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing car 1

  Scenario: Reading a car filtering by engineSize
    When the get request has engineSize params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing car 1

  Scenario: Reading a car filtering by mileage
    When the get request has mileage params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing car 1

  Scenario: Reading a car filtering by age
    When the get request has age params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing car 1

  Scenario: Reading a car filtering by engineSize, returning nothing
    When the get request has engineSizeFail params
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a resultSet containing nothing

