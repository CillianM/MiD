Feature: A party can be created, queried and updated

  Scenario: client makes a successful call to POST /party
    When the client calls /party with party data
    Then the client receives status code of 200
    And the client receives back a reference to the created party

  Scenario: client makes a failed call to POST /party
    When the client calls /party with no party data
    Then the client receives status code of 400

  Scenario: client makes an illegal call to POST /party
    When the client calls /party with incorrect party key
    Then the client receives status code of 400

  Scenario: client makes a successful call to GET /party
    Given the client calls /party with party data
    And the client receives back a reference to the created party
    When the client calls /party with existing partyId
    Then the client receives status code of 200
    And the client receives back data on the referenced party

  Scenario: client makes a failed call to GET /party
    When the client calls /party with nonexistent partyId
    Then the client receives status code of 404

  Scenario: client makes a successful call to PUT /party
    Given the client calls /party with party data
    And the client receives back a reference to the created party
    When the client calls /party with an updated name
    Then the client receives status code of 200
    And the client receives back data on the updated party

  Scenario: client makes a unauthenticated call to PUT /party
    Given the client calls /party with party data
    And the client receives back a reference to the created party
    When the client calls /party without correct credentials
    Then the client receives status code of 403
