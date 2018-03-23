Feature: A user can be created

  Scenario: client makes a successful call to POST /user
    When the client calls /user with user data
    Then the client receives status code of 200
    And the client receives back a reference to the created user

  Scenario: client makes a failed call to POST /user
    When the client calls /user with no user data
    Then the client receives status code of 400