package com.administracion.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase que permite cerrar los recursos de JDBC
 */
public class CerrarRecursosJDBC {

	/**
	 * Metodo que permite cerrar el recurso PreparedStatement
	 */
	public static void closePreparedStatement(PreparedStatement pst) throws Exception {
		if (pst != null) {
			pst.close();
		}
	}

	/**
	 * Metodo que permite cerrar el recurso ResultSet
	 */
	public static void closeResultSet(ResultSet res) throws Exception {
		if (res != null) {
			res.close();
		}
	}

	/**
	 * Metodo que permite cerrar el recurso Statement
	 */
	public static void closeStatement(Statement stm) throws Exception {
		if (stm != null) {
			stm.close();
		}
	}

	/**
	 * Metodo que permite cerrar el recurso Connection SQL
	 */
	public static void closeConnection(Connection connection) throws Exception {
		if (connection != null) {
			connection.close();
		}
	}
}
