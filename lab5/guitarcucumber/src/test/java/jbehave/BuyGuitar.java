package jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import guitarcucumber.Guitar;

import static org.junit.Assert.*;


public class BuyGuitar {

    private boolean expectedStatus = true;

    @Given("Client is logged in")
    public void client_is_logged_in() {
        Guitar g = new Guitar();
        if(g.isLoggedIn() == true) {
            g.setCanClick(true);
        }
    }

    @When("Client clicked $string")
    public void client_clicked(String string) {
        Guitar g = new Guitar();
        if(g.canClick() == true && string == "BUY") {
            g.setClickStatus(true);
        }    
    }

    @Then(value = "Guitar has been sold", priority = 1)
    public void guitar_is_sold() {
        Guitar g = new Guitar();
        g.setSoldStatus(true);
        assertTrue(g.getSoldStatus() == expectedStatus);

    }
} 