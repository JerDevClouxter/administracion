package com.administracion.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import com.administracion.enums.Numero;

/**
 * Clase que contiene las funciones comunes para los procesos de JDBC
 */
public class UtilJDBC {

	/** Instancia unica de la utilidad */
	private static UtilJDBC instance;

	/**
	 * La utilida es un singleton, no se permite instancias por fuera
	 */
	private UtilJDBC() {}

	/**
	 * Metodo que permite dar la unica instancia de la utilidad
	 */
	public static UtilJDBC getInstance() {
		if (instance == null) {
			instance = new UtilJDBC();
		}
		return instance;
	}

	/**
	 * Metodo utilitario para el insert JDBC donde se debe retornar el ID
	 *
	 * @param dml, es la sentencia DML a ejecutar
	 * @param valores, valores a insertar o actualizar en la tabla
	 */
	public Long insertReturningID(Connection con, String dml, ValueSQL... valores) throws Exception {
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(dml);

			// se recorre cada valor para configurarlo en el PreparedStatement
			int posicion = Numero.UNO.valueI.intValue();
			for (ValueSQL valueSQL : valores) {

				// se valida si se debe configurar NULL en el PreparedStatement
				if (valueSQL.getValor() != null) {
					setValorNotNull(pst, valueSQL, posicion);
				} else {
					pst.setNull(posicion, valueSQL.getTipoDato());
				}
				posicion++;
			}

			// se ejecuta la inserción
			res = pst.executeQuery();

			// se obtiene el identificador unico de la insercion
			if (res.next()) {
				return res.getLong(Numero.UNO.valueI);
			}
			return Numero.ZERO.valueL;
		} finally {
			CerrarRecursosJDBC.closePreparedStatement(pst);
			CerrarRecursosJDBC.closeResultSet(res);
		}
	}

	/**
	 * Metodo utilitario para los UPDATES o INSERT con JDBC
	 *
	 * @param dml, es la sentencia DML a ejecutar
	 * @param valores, valores a insertar o actualizar en la tabla
	 */
	public int insertUpdate(Connection con, String dml, ValueSQL... valores) throws Exception {
		PreparedStatement pst = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(dml);

			// se recorre cada valor para configurarlo en el PreparedStatement
			int posicion = Numero.UNO.valueI.intValue();
			for (ValueSQL valueSQL : valores) {

				// se valida si se debe configurar NULL en el PreparedStatement
				if (valueSQL.getValor() != null) {
					setValorNotNull(pst, valueSQL, posicion);
				} else {
					pst.setNull(posicion, valueSQL.getTipoDato());
				}
				posicion++;
			}

			// se ejecuta la inserción
			return pst.executeUpdate();
		} finally {
			CerrarRecursosJDBC.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo que ejecuta multiples sentencias DML por BATCH de JDBC con Injection
	 *
	 * @param dml, Es la sentencia DML a ejecutar en el BATCH
	 * @param injections, cantidad de veces que se debe ejecutar este DML
	 */
	public void batchConInjection(Connection con, String dml, List<List<ValueSQL>> injections) throws Exception {
		PreparedStatement pst = null;
		try {
			// se establece el PreparedStatement
			pst = con.prepareStatement(dml);

			// constante numerico para el proceso
			final int ZERO = Numero.ZERO.valueI.intValue();
			final Integer UNO = Numero.UNO.valueI;
			final Integer MIL = Numero.MIL.valueI;

			// lleva la cuenta de DML agregados en el BATCH
			int countDML = ZERO;

			// se recorre la cantidad de injections agregar en el BATCH
			int posicion;
			for (List<ValueSQL> values : injections) {

				// se injecta los parametros de esta sentencia y se agrega al BATCH
				posicion = UNO;
				for (ValueSQL valueSQL : values) {

					// se valida si se debe configurar NULL en el PreparedStatement
					if (valueSQL.getValor() != null) {
						setValorNotNull(pst, valueSQL, posicion);
					} else {
						pst.setNull(posicion, valueSQL.getTipoDato());
					}
					posicion++;
				}

				// se agrega el DML al BATCH y se suma a la cuenta
				pst.addBatch();
				countDML++;

				// se valida si se debe ejecutar el BATCH
				if (!UNO.equals(countDML) && (countDML % MIL == ZERO)) {
					pst.executeBatch();
				}
			}

			// se ejecuta el ultimo bloque y se confirman los cambios
			pst.executeBatch();
		} finally {
			CerrarRecursosJDBC.closePreparedStatement(pst);
		}
	}

	/**
	 * Metodo que ejecuta multiples sentencias DML por BATCH de JDBC sin Injection
	 *
	 * @param dmls, lista de sentencias DMLS a ejecutar en el BATCH
	 */
	public void batchSinInjection(Connection con, List<String> dmls) throws Exception {
		// solo aplica si hay DMLS
		if (dmls != null && !dmls.isEmpty()) {
			Statement stm = null;
			try {
				// se establece el Statement
				stm = con.createStatement();

				// constante numerico para el proceso
				final int ZERO = Numero.ZERO.valueI.intValue();
				final Integer UNO = Numero.UNO.valueI;
				final Integer MIL = Numero.MIL.valueI;

				// lleva la cuenta de DML agregados en el BATCH
				int countDML = ZERO;

				// se recorre todos los DMLs que deben ser ejecutados por el BATCH
				for (String dml : dmls) {

					// se agrega el DML al BATCH y se suma a la cuenta
					stm.addBatch(dml);
					countDML++;

					// se valida si se debe ejecutar el BATCH
					if (!UNO.equals(countDML) && (countDML % MIL == ZERO)) {
						stm.executeBatch();
					}
				}

				// se ejecuta el ultimo bloque y se confirman los cambios
				stm.executeBatch();
			} finally {
				CerrarRecursosJDBC.closeStatement(stm);
			}
		}
	}

	/**
	 * Metodo que permite obtener la connection de una transaccion de HIBERNATE
	 */
	public Connection getConnection(EntityManager entityManager) {
		Session session = entityManager.unwrap(Session.class);
		ConnectionJDBC myWork = new ConnectionJDBC();
		session.doWork(myWork);
		return myWork.getConnection();
	}

	/**
	 * Metodo que permite settear un valor NOT NULL al PreparedStatement
	 */
	private void setValorNotNull(PreparedStatement pst, ValueSQL valor, int posicion) throws Exception {
		switch (valor.getTipoDato()) {
			case Types.VARCHAR:
				pst.setString(posicion, (String) valor.getValor());
				break;

			case Types.INTEGER:
				pst.setInt(posicion, (Integer) valor.getValor());
				break;

			case Types.BIGINT:
				pst.setLong(posicion, (Long) valor.getValor());
				break;

			case Types.DATE:
				pst.setDate(posicion, new java.sql.Date(((Date) valor.getValor()).getTime()));
				break;

			case Types.TIMESTAMP:
				pst.setTimestamp(posicion, new java.sql.Timestamp(((Date) valor.getValor()).getTime()));
				break;

			case Types.BLOB:
				pst.setBytes(posicion, (byte[]) valor.getValor());
				break;

			case Types.DECIMAL:
				pst.setBigDecimal(posicion, (BigDecimal) valor.getValor());
				break;
		}
	}
}
