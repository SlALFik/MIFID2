Feature: Google Search
  As a user I want to be able to search info in google

  Scenario: Verify DB search
    Given I open google home page
    When I search for DB website
    Then DB website is first result


  Scenario: Verify booking search
    Given I open google home page
    When I search for booking website
    Then booking website is first result

  Scenario: Verify SlALFik search
    Given I open google home page
    When I search for booking website
    Then SlALFik website is first result
