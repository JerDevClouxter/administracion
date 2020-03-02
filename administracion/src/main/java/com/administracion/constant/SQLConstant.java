package com.administracion.constant;

/**
 * Clase constante que contiene los DMLs Y DDLs para las consultas nativas
 */
public class SQLConstant {

	public static final String SELECT_EMPRESAS_BASE = "SELECT e.ID_EMPRESA,e.NIT_EMPRESA,e.RAZON_SOCIAL FROM EMPRESAS e ORDER BY e.RAZON_SOCIAL";
	

}
