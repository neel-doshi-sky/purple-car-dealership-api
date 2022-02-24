Feature: Testing the update endpoint
    Scenario: Updating an existing car
        When the put request has an id parameter that exists
        And the user calls the update endpoint
        Then the user should get a response status code of 200
        And a "message" of "Updated car with ID: 1"
        And a result equal to car 1

