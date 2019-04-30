  Feature: Remove guitar from the list
  Discontinuing a guitar
  Scenario Outline: Admin takes down a guitar
    Given Admin is logged in
    When Admin chose guitar "<manufacturer>" "<model>" 
    Then System shows "<result>"

    Examples:
      | manufacturer   | model  | result       |
      | Schecter       | Omen   | deleted      |
      | Ibanez         | Xiphos | doesnt exist |
