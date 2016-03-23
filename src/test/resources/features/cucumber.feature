Feature: Cucumber Home
  As a user I want to be able to read documentation

  Scenario: Verify documentation is present
    Given I open cucumber home page
    When I go to doc page
    Then doc page is opened


