package jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import guitarcucumber.Guitar;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class RemoveGuitar {

    private List<Guitar> list2 = new ArrayList<>();
    Guitar guitarToDelete;
    private String manufacturer;
    private String model;

    @Given("Admin is logged in")
    public void admin_is_logged_in() {

         list2.add(new Guitar("Schecter", "Omen", 6));
         list2.add(new Guitar("Dean", "Vendetta", 7));
     }

    @When("Admin chose manufacturer $manufacturer")
    public void admin_chose_manufacturer(String mf) {
        this.manufacturer = mf;
    }

    @When("Admin chose manufacturer $model")
    public void admin_chose_model(String m) {
        this.model = m;
    }

    @Then("System shows {string}")
    public void system_shows(String string) {
       guitarToDelete = new Guitar(manufacturer, model, 6);
     //Guitar listObjectToRemove = list2.stream().filter(guitar -> guitar.getManufacturer().equals(guitarToDelete.getManufacturer().findFirst().get()));
        if(list2.remove(list2.get(0))) {
             string = "deleted";
        }
        assertEquals(1, list2.size());
    }
}