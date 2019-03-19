package pl.tau.s15664.domain;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class GuitarTest {
    @Test
    public void createObjectTest() {
        Guitar g = new Guitar();
        assertNotNull(g);
    }

    @Test
    public void guitarGettersAndSettersTest() {
        Guitar g = new Guitar();
        g.setId(1);
        g.setManufacturer("Jackson");
        g.setModel("Warrior");
        g.setNumberOfStrings(6);
        assertEquals(new Long(1), g.getId());
        assertEquals("Jackson", g.getManufacturer());
        assertEquals("Warrior", g.getModel());
        assertEquals(new Long(6), g.getNumberOfStrings());
    }
}