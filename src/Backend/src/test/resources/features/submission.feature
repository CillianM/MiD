Feature: A submission can be created, queried, viewed and updated

  Background: A current party and user exist on the server
    Given the client calls /party with party data
    Then the client receives status code of 200
    And the client receives back a reference to the created party
    Then the client calls /user with user data
    And the client receives status code of 200
    And the client receives back a reference to the created user

  Scenario: client makes a successful call to POST /submission
    When the client calls /submission for an existing party
    Then the client receives status code of 200
    And the client receives back a reference to the created submission

  Scenario: client makes an unsuccessful call to POST /submission
    When the client calls /submission for an nonexistent party
    Then the client receives status code of 404

  Scenario: client makes an unauthorized call to POST /submission
    When the client calls /submission using incorrect auth headers
    Then the client receives status code of 403

  Scenario: client makes a successful call to PUT /submission
    Given the client calls /submission for an existing party
    And the client receives back a reference to the created submission
    When the client calls /submission to update an existing submission as a party
    Then the client receives status code of 200
    And the client receives back a reference to the updated submission

  Scenario: client makes a successful call to PUT /submission
    Given the client calls /submission for an existing party
    And the client receives back a reference to the created submission
    When the client calls /submission to update an existing submission as a user
    Then the client receives status code of 403

  Scenario: client makes a successful call to GET /submission
    Given the client calls /submission for an existing party
    And the client receives back a reference to the created submission
    When the client calls /submission to get current status of their submission
    Then the client receives status code of 200
    And the client receives back a reference to the current submission

  Scenario: client makes a successful call to GET /submission
    Given the client calls /submission for an existing party
    And the client receives back a reference to the created submission
    Then the client calls /user with user data
    And the client receives back a reference to the created user
    When the client calls /submission to get current status of a submission thats not theirs
    Then the client receives status code of 403
