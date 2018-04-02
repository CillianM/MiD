Feature: A user can be created, queried and updated

  Scenario: client makes a successful call to POST /user
    When the client calls /user with user data
    Then the client receives status code of 200
    And the client receives back a reference to the created user

  Scenario: client makes a failed call to POST /user
    When the client calls /user with no user data
    Then the client receives status code of 400

  Scenario: client makes an illegal call to POST /user
    When the client calls /user with incorrect user key
    Then the client receives status code of 400

  Scenario: client makes a successful call to GET /user
    Given the client calls /user with user data
    And the client receives back a reference to the created user
    When the client calls /user with existing userId
    Then the client receives status code of 200
    And the client receives back data on the referenced user

  Scenario: client makes a failed call to GET /user
    When the client calls /user with nonexistent userId
    Then the client receives status code of 404

  Scenario: client makes a successful call to PUT /user/token
    Given the client calls /user with user data
    And the client receives back a reference to the created user
    When the client calls /user/token with an updated fcmtoken
    Then the client receives status code of 200
    And the client receives back data on the updated user

  Scenario: client makes a unauthenticated call to PUT /user/token
    Given the client calls /user with user data
    And the client receives back a reference to the created user
    When the client calls /user/token without correct credentials
    Then the client receives status code of 403
