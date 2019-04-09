package s15664;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CalcTest {

    Calc calc;

    @Before
    public void init() {
       calc = new Calc();
    }
    @Test
    public void calcExistsCheck() {
        assertNotNull(calc);
    }

    @Test
    public void timesTenCheck() {
        double x = 2;
        double sum = calc.sum(x,x);
        assertEquals(4, sum ,0.001);
    }
}

