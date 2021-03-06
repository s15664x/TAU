package pl.tau.s15664.dao;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.tau.s15664.domain.Guitar;
import java.sql.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class GuitarDaoTest {
    private static final Logger LOGGER = Logger.getLogger(GuitarDaoTest.class.getCanonicalName());

    @Rule
    public Timeout globalTimeout = new Timeout(1000);

    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    GuitarDao guitarManager;
    List<Guitar> expectedDbState;

    @Before
    public void setup() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.createStatement()
                    .executeUpdate("CREATE TABLE " +
                            "Guitar(id bigint GENERATED BY DEFAULT AS IDENTITY, "
                            + "manufacturer varchar(32) NOT NULL, " + "model varchar(32) NOT NULL, "
                            + "numberofstrings integer)");

        } catch (SQLException e) {}

        Random rand = new Random();
        PreparedStatement addGuitarStmt = connection.prepareStatement(
                "INSERT INTO Guitar (manufacturer, model, numberofstrings) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        expectedDbState = new LinkedList<Guitar>();
        for (int i = 0; i < 10; i++) {
            Guitar guitar = new Guitar("Fender", "Stratocaster" + rand.nextInt(1000), 6);
            try {
                addGuitarStmt.setString(1, guitar.getManufacturer());
                addGuitarStmt.setString(2, guitar.getModel());
                addGuitarStmt.setInt(3, guitar.getNumberOfStrings());
                addGuitarStmt.executeUpdate();
                ResultSet generatedKeys = addGuitarStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    guitar.setId(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
            }

            expectedDbState.add(guitar);
        }
        guitarManager = new GuitarDaoJdbcImpl(connection);
    }

    @After
    public void cleanup() throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.prepareStatement("DELETE FROM Guitar").executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.FINEST,"Probably the database was not yet initialized");
        }
    }

    @Test
    public void checkAdding() throws Exception {
        Guitar guitar = new Guitar();
        guitar.setManufacturer("Ibanez");
        guitar.setModel("Xiphos");
        guitar.setNumberOfStrings(7);

        guitarManager.addGuitar(guitar);
        expectedDbState.add(guitar);
        assertThat(guitarManager.getAllGuitars(), equalTo(expectedDbState));
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void checkAddingFailure() throws Exception {
        Guitar guitar = new Guitar();
        guitar.setManufacturer("Ibanez");

        guitarManager.addGuitar(guitar);
        expectedDbState.add(guitar);
        assertThat(guitarManager.getAllGuitars(), equalTo(expectedDbState));
    }

    @Test
    public void checkGetting() throws Exception {
        Guitar guitar = expectedDbState.get(7);
        assertEquals(guitar, guitarManager.getGuitar(guitar.getId()));
    }

    @Test(expected = Exception.class)
    public void checkGettingFailure() throws Exception {
        Guitar guitar = expectedDbState.get(69876);
        guitarManager.getGuitar(guitar.getId());
    }

    @Test()
    public void checkDeleting() throws SQLException {
        Guitar guitar = expectedDbState.get(3);
        expectedDbState.remove(guitar);
        guitarManager.deleteGuitar(guitar);
        assertThat(guitarManager.getAllGuitars(), equalTo(expectedDbState));
    }

    @Test(expected = SQLException.class)
    public void checkDeletingException() throws SQLException {
        Guitar guitar = expectedDbState.get(4);
        guitarManager.deleteGuitar(guitar);
        guitarManager.getGuitar(guitar.getId());
    }


    @Test()
    public void checkUpdatingSuccess() throws SQLException {
        Guitar guitar = expectedDbState.get(3);
        guitar.setModel("Vendetta");
        expectedDbState.set(3, guitar);
        assertEquals(1, guitarManager.updateGuitar(guitar));
        assertThat(guitarManager.getAllGuitars(), equalTo(expectedDbState));
    }

    @Test(expected = SQLException.class)
    public void checkUpdatingFailure() throws SQLException {
        Guitar guitar = new Guitar("Schecter", "Hellraiser", 8);
        assertEquals(1, guitarManager.updateGuitar(guitar));
    }

}