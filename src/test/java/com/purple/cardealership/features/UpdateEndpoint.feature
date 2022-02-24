Feature: Testing the update endpoint
    Scenario: Updating an existing car
        When the get request has no params
        And the user calls the get endpoint
        Then the user should get a response status code of 200
        And a "message" of "Successfully fetched cars"
        And a resultSet containing car 1