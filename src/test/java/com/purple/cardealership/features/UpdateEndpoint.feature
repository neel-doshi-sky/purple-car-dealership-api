Feature: Testing the update endpoint
    Scenario: Updating an existing car
        Given that we already have a car
        When the put request has an id parameter that exists
        And the user calls the update endpoint
        Then the user should get a response status code of 200
        And a update message
        And a result equal to car 1

