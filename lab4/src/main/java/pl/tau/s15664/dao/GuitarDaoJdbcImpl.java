package pl.tau.s15664.dao;

import pl.tau.s15664.dao.GuitarDao;
import pl.tau.s15664.domain.Guitar;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class GuitarDaoJdbcImpl implements GuitarDao {

    public PreparedStatement preparedStatementGetAll;
    public PreparedStatement preparedStatementInsert;
    public PreparedStatement preparedStatementDelete;
    public PreparedStatement preparedStatementUpdate;

    Connection connection;
    
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        preparedStatementGetAll = connection.prepareStatement(
                "SELECT id, manufacturer, model, numberofstrings FROM Guitar ORDER BY id");
        preparedStatementInsert= connection.prepareStatement(
                "INSERT INTO Guitar (manufacturer, model, numberofstrings) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatementUpdate= connection.prepareStatement(
                "UPDATE Guitar SET manufacturer = ?, model = ?, numberofstrings = ? WHERE id = ?",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatementDelete= connection.prepareStatement(
                "DELETE FROM Guitar WHERE id = ?",
                Statement.RETURN_GENERATED_KEYS);
    }


    @Override
    public List<Guitar> getAllGuitars() {
        try {
            List<Guitar> ret = new LinkedList<>();
            ResultSet result = preparedStatementGetAll.executeQuery();
            while(result.next()) {
                Guitar g = new Guitar();
                g.setId(result.getLong("id"));
                g.setManufacturer(result.getString("manufacturer"));
                g.setModel(result.getString("model"));
                g.setNumberOfStrings(result.getInt("numberofstrings"));
                ret.add(g);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addGuitar(Guitar guitar) throws SQLException {
        preparedStatementInsert.setString(1, guitar.getManufacturer());
        preparedStatementInsert.setString(2, guitar.getModel());
        preparedStatementInsert.setInt(3, guitar.getNumberOfStrings());
        int r = preparedStatementInsert.executeUpdate();
        return r;
    }

    @Override
    public int updateGuitar(Guitar guitar) throws SQLException {
        preparedStatementUpdate.setString(1, guitar.getManufacturer());
        preparedStatementUpdate.setString(2, guitar.getModel());
        preparedStatementUpdate.setInt(3, guitar.getNumberOfStrings());
        if (guitar.getId() != null) {
            preparedStatementUpdate.setLong(4, guitar.getId());
        } else {
            preparedStatementUpdate.setLong(4, -1);
        }
        int r = preparedStatementUpdate.executeUpdate();
        return r;
    }

    @Override
    public int deleteGuitar(Guitar guitar) throws SQLException {
        if (guitar.getId() != null) {
            preparedStatementDelete.setLong(1, guitar.getId());
        } else {
            preparedStatementDelete.setLong(1, -1);
        }
        int r = preparedStatementDelete.executeUpdate();
        return r;
    } 
}
