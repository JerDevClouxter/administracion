package com.administracion.constant;

/**
* Clase constante que contiene los DML Y DDL que se utilizan en todos los modulos
*/
public class SQLTransversal {

	/**
	 * Metodo que permite construir el paginador SQL para las consultas
	 */
	public static void getSQLPaginator(String skip, String rowsPage, StringBuilder sql) {
		sql.append(" OFFSET ").append(skip).append(" LIMIT ").append(rowsPage);
	}

	/**
	 * Metodo que permite construir el COUNT para obtener la cantidad de una consulta
	 */
	public static String getSQLCount(String from) {
		StringBuilder sql = new StringBuilder("SELECT COUNT(*)");
		sql.append(from);
		return sql.toString();
	}
}
