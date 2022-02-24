Feature: Testing car dealership
  Scenario: Creating a car with valid data
    When the body contains "valid" data
    And the user calls the create endpoint
    Then the user should get a response status code of 201
    And a "message" of "The car was created"

  Scenario: Creating a car with invalid data
    When the body contains "invalid" data
    And the user calls the create endpoint
    Then the user should get a response status code of 400
    And a "error" of "Missing/invalid data in post body"
    And a "internal-error-code" of "CUSTOM_ERROR_BAD_REQUEST"

  Scenario: Reading a car with no parameters
    And the user calls the get endpoint
    Then the user should get a response status code of 200
    And a "message" of "Successfully fetched cars"
    And a results of car 1


