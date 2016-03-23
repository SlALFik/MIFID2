Feature: Google Add via Search
  As a user I want to be able to add numerical in google

  Scenario: add 2 numericals
    Given I open google home page
    When first additive is 500
    And second additive is 250
    Then result is 750



  Scenario Outline: test 5 additions
    Given I open google home page
    When first additive is <additive1>
    And second additive is <additive2>
    Then result is <result>

    Examples:
      | additive1 | additive2 | result |
      |    10     |    10     |   20   |
      |    15     |    10     |   25   |
      |    25     |    25     |   50   |
      |    150    |    100    |   250  |
      |    100    |    150    |   150  |