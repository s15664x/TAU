Feature: Buy guitar from the shop
  Basic shop functionality
  Scenario: Client buys Fender Stratocaster
    Given Client is logged in
    When Client clicked "BUY"
    Then Guitar is sold