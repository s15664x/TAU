Scenario: Admin removes a guitar from the shop

    Given Admin is logged in
    When Admin chose manufacturer <manufacturer>
    And Admin chose model <model>
    Then System shows <result>

    Examples:
      | manufacturer   | model  | result       |
      | Schecter       | Omen   | deleted      |
      | Ibanez         | Xiphos | doesnt exist |
