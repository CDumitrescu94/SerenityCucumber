# new feature
# Tags: optional
    
Feature: Repository issues filtering


Scenario: Filter issues with wrong argument
    Given the client uses the following url parameters:
        |state |opem |
    When the client calls 'GET_ISSUES' endpoint
    Then the client should receive an HTTP 422 response code
        And the client using jsonPath 'message' should see 'Validation Failed'

Scenario: Filter issues after state open
    Given the client uses the following url parameters:
        |state |open |
        |direction |desc |
    When the client calls 'GET_ISSUES' endpoint with success
    Then the client using jsonPath '[0].title' should see 'Open issue 3'
        And the client using jsonPath '[1].title' should see 'Open issue'
        And the client using jsonPath '[2].title' should see 'Api test'

Scenario: Filter issues after state closed
    Given the client uses the following url parameters:
      |state |closed |
    When the client calls 'GET_ISSUES' endpoint with success
    Then the client using jsonPath '[0].title' should see 'Closed issue 1'

Scenario: Filter issues after state open and label bug
    Given the client uses the following url parameters:
        |state |open |
        |labels |bug |
    When the client calls 'GET_ISSUES' endpoint with success
    Then the client using jsonPath '[0].title' should see 'Open issue'

