package guitarcucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import guitarcucumber.GuitarSearch;
import guitarcucumber.Guitar;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class Stepdefs {

    private boolean expectedStatus = true;

    @Given("Client is logged in")
    public void client_is_logged_in() {
        Guitar g = new Guitar();
        if(g.isLoggedIn() == true) {
            g.setCanClick(true);
        }
    }

    @When("Client clicked {string}")
    public void client_clicked(String string) {
        Guitar g = new Guitar();
        if(g.canClick() == true && string == "BUY") {
            g.setClickStatus(true);
        }    
    }

    @Then("Guitar is sold")
    public void guitar_is_sold() {
        Guitar g = new Guitar();
        g.setSoldStatus(true);
        assertTrue(g.getSoldStatus() == expectedStatus);
    }

    private List<Guitar> list = new ArrayList<>();
    private String manufacturer;
    private String model;
    private boolean didntChooseOther;

    @Given("Client uses search box")
    public void client_uses_search_box() {
        GuitarSearch gs = new GuitarSearch();
            if(gs.getUsesSearchBox() == true) {
                list.add(new Guitar("Schecter", "Omen", 6));
                list.add(new Guitar("Dean", "Vendetta", 7));
            }
    }

    @When("Client chose manufacturer {string}")
    public void client_chose_manufacturer(String mf) {
        manufacturer = mf;
    }

    @When("Client chose model {string}")
    public void client_chose_model(String m) {
        model = m;
    }

    @When("Client did not choose any other manufacturers and models")
    public void client_did_not_choose_any_other_manufacturers_and_models() {
        didntChooseOther = true;
    }

    @Then("Guitar is found")
    public void guitar_is_found() {
        if(didntChooseOther == true) {
        Guitar guitarr = new Guitar(manufacturer,model,6);

        assertEquals(true, guitarr.equals(list.get(0)));

        }
    }

    private List<Guitar> list2 = new ArrayList<>();
    Guitar guitarToDelete;

    @Given("Admin is logged in")
    public void admin_is_logged_in() {
  
            list2.add(new Guitar("Schecter", "Omen", 6));
            list2.add(new Guitar("Dean", "Vendetta", 7));
        }

    @When("Admin chose guitar {string} {string}")
    public void admin_chose_guitar_numberofstrings(String string, String string2) {
        guitarToDelete = new Guitar(string, string2, 6);
    }

    @Then("System shows {string}")
    public void system_shows(String string) {
        //Guitar listObjectToRemove = list2.stream().filter(guitar -> guitar.getManufacturer().equals(guitarToDelete.getManufacturer().findFirst().get()));
        if(list2.remove(list2.get(0))) {
            string = "deleted";
        }
        assertEquals(1, list2.size());
    }
}