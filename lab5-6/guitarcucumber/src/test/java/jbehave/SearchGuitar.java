package jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import guitarcucumber.Guitar;
import guitarcucumber.GuitarSearch;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class SearchGuitar {

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

    @When("Client chose manufacturer $manufacturer")
    public void client_chose_manufacturer(String mf) {
        manufacturer = mf;
    }

    @When("Client chose model $model")
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
}