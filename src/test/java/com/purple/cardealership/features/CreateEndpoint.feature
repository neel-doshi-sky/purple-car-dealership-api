Feature: Testing create endpoint
  Scenario: Creating a car with valid data
    When the body contains "valid" data
    And the user calls the create endpoint
    Then the user should get a response status code of 201
    And a 201 of the car was created with id:

  Scenario: Creating a car with invalid data
    When the body contains "invalid" data
    And the user calls the create endpoint
    Then the user should get a response status code of 400
    And a "error" of "Missing/invalid data in post body"
    And a "internal-error-code" of "CUSTOM_ERROR_BAD_REQUEST"
