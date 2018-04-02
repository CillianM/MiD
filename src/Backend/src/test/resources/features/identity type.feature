Feature: An identity type can be created, viewed and updated

  Background: A party exists on the server
    Given the client calls /party with party data
    And the client receives back a reference to the created party
    When the client calls /party with existing partyId
    Then the client receives status code of 200

  Scenario: A client makes a successful call to POST /identitytype
    When the client calls /identitytype with identitytype data
    Then the client receives status code of 200
    And the client receives back a reference to the created identitytype

  Scenario: A client makes an unsuccessful call to POST /identitytype
    When the client calls /identitytype with incorrect field types
    Then the client receives status code of 400

  Scenario: A client makes a unauthorized call to POST /identitytype
    When the client calls /identitytype with incorrect auth headers
    Then the client receives status code of 403

  Scenario: A client makes a successful call to PUT /identitytype
    Given the client calls /identitytype with identitytype data
    And the client receives back a reference to the created identitytype
    When the client calls /identitytype with an updated field type
    Then the client receives status code of 200
    And the client receives back a reference to the updated identitytype

  Scenario: A client makes an unsuccessful call to PUT /identitytype
    Given the client calls /identitytype with identitytype data
    And the client receives back a reference to the created identitytype
    When the client calls /identitytype with an incorrectly updated field type
    Then the client receives status code of 400
