package pl.tau.s15664.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pl.tau.s15664.domain.Guitar;

public interface GuitarDao {
	public Connection getConnection();
	public void setConnection(Connection connection) throws SQLException;
	public List<Guitar> getAllGuitars();
	public int addGuitar(Guitar guitar) throws SQLException;
	public int deleteGuitar(Guitar guitar) throws SQLException;
	public int updateGuitar(Guitar guitar) throws SQLException;
}