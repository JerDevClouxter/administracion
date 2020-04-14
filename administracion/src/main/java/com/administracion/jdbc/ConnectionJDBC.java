package com.administracion.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

/**
 * Clase que permite representar un WORK para la conection de hibernate
 */
public class ConnectionJDBC implements Work {

	/** Instancia de la connection obtenida de hibernate */
	private Connection connection;

	@Override
	public void execute(Connection connection) throws SQLException {
		this.connection = connection;
	}

	/**
	 * Metodo que permite dar la connection activo de hibernate
	 */
	public Connection getConnection() {
		return connection;
	}
}
