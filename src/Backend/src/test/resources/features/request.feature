Feature: A request can be created, viewed and responded to

  Background: A current party and user exist on the server
    Given the client calls /party with party data
    And the client receives back a reference to the created party
    Then the client calls /identitytype with identitytype data
    And  the client receives back a reference to the created identitytype
    Then the client calls /user with user data
    And the client receives back a reference to the created user
    Then another client creates a user
    And the client receives back a reference to the second user

  Scenario: One client sends a request to another client successfully
    When the requesting client sends a request to another user
    Then the client receives status code of 200
    And the requesting client receives back a reference to the created request
    And the client can ask to view the request
    Then the client receives status code of 200
    And the client receives back a reference to the the request

  Scenario: One client sends a request to another client using a nonexistent identity type
    When the requesting client sends a request to another user with a nonexistent identitytype
    Then the client receives status code of 404

  Scenario: One client sends a request to another client using fields not present in the selected identity type
    When the requesting client sends a request to another user with incorrect identity fields
    Then the client receives status code of 400

  Scenario: Receiving client responds to a request successfully
    Given the client calls /submission for an existing party
    Then the client receives status code of 200
    And the client receives back a reference to the created submission
    Then the client calls /submission to update an existing submission as a party
    Then the client receives status code of 200
    And the client receives back a reference to the updated submission
    When the requesting client sends a request to another user
    Then the client receives status code of 200
    And the requesting client receives back a reference to the created request
    When the client responds to the request
    Then the client receives status code of 200
    And the client receives back a reference to the updated request
    And the requesting client can ask to view the update to the request
    Then the client receives status code of 200
    And the requesting client receives back a reference to the the request

  Scenario: Sending client responds to a request unsuccessfully due to the fact they're not authorized
    When the requesting client sends a request to another user
    Then the client receives status code of 200
    And the requesting client receives back a reference to the created request
    When the requesting client responds to the request
    Then the client receives status code of 403

  Scenario: Receiving client responds to a request unsuccessfully due to no certificate
    When the requesting client sends a request to another user
    Then the client receives status code of 200
    And the requesting client receives back a reference to the created request
    When the client responds to the request to the request with no certificate
    Then the client receives status code of 404

  Scenario: Receiving client responds to a request unsuccessfully due to new fields not present
    Given the client calls /submission for an existing party
    Then the client receives status code of 200
    And the client receives back a reference to the created submission
    Then the client calls /submission to update an existing submission as a party
    Then the client receives status code of 200
    And the client receives back a reference to the updated submission
    When the requesting client sends a request to another user
    Then the client receives status code of 200
    And the requesting client receives back a reference to the created request
    When the client responds to the request with updated fields
    Then the client receives status code of 400


  Scenario: Receiving client responds to a request unsuccessfully due to unknown field entries
    Given the client calls /submission for an existing party
    Then the client receives status code of 200
    And the client receives back a reference to the created submission
    Then the client calls /submission to update an existing submission as a party
    Then the client receives status code of 200
    And the client receives back a reference to the updated submission
    When the requesting client sends a request to another user
    Then the client receives status code of 200
    And the requesting client receives back a reference to the created request
    When the client responds to the request with unknown fields
    Then the client receives status code of 400
