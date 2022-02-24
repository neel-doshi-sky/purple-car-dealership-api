Feature: Testing car dealership
  Scenario: Creating a car with valid data
    When the body contains valid data
    And the user calls the create endpoint
    Then the user should get a response status code of 201
#    And a message of "The car was created"
