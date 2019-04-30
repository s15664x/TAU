Scenario: Client searches for "Schecter Omen"
    Given Client uses search box
    When Client chose manufacturer "Schecter"
    And Client chose model "Omen"
    But Client did not choose any other manufacturers and models
    Then Guitar is found