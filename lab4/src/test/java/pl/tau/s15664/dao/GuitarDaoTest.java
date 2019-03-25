package pl.tau.s15664.dao;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import pl.tau.s15664.domain.Guitar;
import java.sql.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@RunWith(MockitoJUnitRunner.class)
public class GuitarDaoTest {
    Logger LOGGER = Logger.getLogger(GuitarDaoTest.class.getCanonicalName());
    Random random;
    static List<Guitar> initialDatabaseState;

    abstract class AbstractResultSet implements ResultSet {
        int i;

        @Override
        public int getInt(String s) throws SQLException {
            return initialDatabaseState.get(i-1).getNumberOfStrings();
        }

        @Override
        public long getLong(String s) throws SQLException {
            return initialDatabaseState.get(i-1).getId();
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            if(columnLabel=="manufacturer"){
                return initialDatabaseState.get(i-1).getManufacturer();
            } else if(columnLabel=="model"){
                return initialDatabaseState.get(i-1).getModel();
            } else return "0";
        }

        @Override
        public boolean next() throws SQLException {
            i++;
            if (i > initialDatabaseState.size())
                return false;
            else
                return true;
        }
    }

    @Mock
    Connection connection;
    @Mock
    PreparedStatement selectStatementMock;
    @Mock
    PreparedStatement insertStatementMock;
    @Mock
    PreparedStatement updateStatementMock;
    @Mock
    PreparedStatement deleteStatementMock;
        


    @Before
    public void setup() throws SQLException {
        random = new Random();
        initialDatabaseState = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Guitar guitar = new Guitar();
            guitar.setId(i);
            guitar.setManufacturer("Fender"+random.nextInt(1000));
            guitar.setModel("Stratocaster"+random.nextInt(1000));
            guitar.setNumberOfStrings(6);
            initialDatabaseState.add(guitar);
        }
        Mockito.when(connection.prepareStatement("SELECT id, manufacturer, model, numberofstrings FROM Guitar ORDER BY id")).thenReturn(selectStatementMock);
        Mockito.when(connection.prepareStatement("INSERT INTO Guitar (manufacturer, model, numberofstrings) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS)).thenReturn(insertStatementMock);
        Mockito.when(connection.prepareStatement("UPDATE Guitar SET manufacturer = ?, model = ?, numberofstrings = ? WHERE id = ?",Statement.RETURN_GENERATED_KEYS)).thenReturn(updateStatementMock);
        Mockito.when(connection.prepareStatement("DELETE FROM Guitar WHERE id = ?",Statement.RETURN_GENERATED_KEYS)).thenReturn(deleteStatementMock);
    }


    @Test
    public void setConnectionCheck() throws SQLException {
        GuitarDaoJdbcImpl dao = new GuitarDaoJdbcImpl();
        dao.setConnection(connection);
        assertNotNull(dao.getConnection());
        assertEquals(dao.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesCheck() throws SQLException {
        GuitarDaoJdbcImpl dao = new GuitarDaoJdbcImpl();
        dao.setConnection(connection);
        assertNotNull(dao.preparedStatementGetAll);
        Mockito.verify(connection).prepareStatement("SELECT id, manufacturer, model, numberofstrings FROM Guitar ORDER BY id");
    }

    @Test
    public void getAllCheck() throws SQLException {

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("manufacturer")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(mockedResultSet.getInt("numberofstrings")).thenCallRealMethod();
        when(selectStatementMock.executeQuery()).thenReturn(mockedResultSet);

        GuitarDaoJdbcImpl dao = new GuitarDaoJdbcImpl();
        dao.setConnection(connection);
        List<Guitar> retrievedGuitars = dao.getAllGuitars();
        assertThat(retrievedGuitars, equalTo(initialDatabaseState));

        verify(selectStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(initialDatabaseState.size())).getLong("id");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("manufacturer");
        verify(mockedResultSet, times(initialDatabaseState.size())).getString("model");
        verify(mockedResultSet, times(initialDatabaseState.size())).getInt("numberofstrings");
        verify(mockedResultSet, times(initialDatabaseState.size()+1)).next();
    }

    @Test
    public void checkAddingInOrder() throws Exception {

        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        GuitarDaoJdbcImpl dao = new GuitarDaoJdbcImpl();
        dao.setConnection(connection);
        Guitar g = new Guitar();
        g.setId(128);
        g.setManufacturer("Ibanez");
        g.setModel("Xiphos");
        g.setNumberOfStrings(7);
        dao.addGuitar(g);


        inorder.verify(insertStatementMock, times(1)).setString(1, "Ibanez");
        inorder.verify(insertStatementMock, times(1)).setString(2, "Xiphos");
        inorder.verify(insertStatementMock, times(1)).setInt(3, 7);

        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void checkUpdatingInOrder() throws Exception {

        InOrder inorder = inOrder(updateStatementMock);
        when(updateStatementMock.executeUpdate()).thenReturn(1);

        GuitarDaoJdbcImpl dao = new GuitarDaoJdbcImpl();
        dao.setConnection(connection);
        Guitar guitar = new Guitar();
        guitar.setId(128);
        guitar.setManufacturer("Epiphone");
        guitar.setModel("Les Paul");
        guitar.setNumberOfStrings(6);
        dao.updateGuitar(guitar);

        inorder.verify(updateStatementMock, times(1)).setString(1, "Epiphone");
        inorder.verify(updateStatementMock, times(1)).setString(2, "Les Paul");
        inorder.verify(updateStatementMock, times(1)).setInt(3, 6);
        inorder.verify(updateStatementMock, times(1)).setLong(4, 128);

        inorder.verify(updateStatementMock).executeUpdate();
    }

    @Test
    public void checkDeletingInOrder() throws Exception {

        InOrder inorder = inOrder(deleteStatementMock);
        when(deleteStatementMock.executeUpdate()).thenReturn(1);

        GuitarDaoJdbcImpl dao = new GuitarDaoJdbcImpl();
        dao.setConnection(connection);
        Guitar g = new Guitar();
        g.setId(128);

        dao.deleteGuitar(g);
       
        inorder.verify(deleteStatementMock, times(1)).setLong(1, 128);

        inorder.verify(deleteStatementMock).executeUpdate();
    } 
}