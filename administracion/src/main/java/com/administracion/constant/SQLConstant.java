package com.administracion.constant;

import com.administracion.enums.EstadoEnum;

/**
 * Clase constante que contiene los DMLs Y DDLs para las consultas nativas
 */
public class SQLConstant {

public static final String SELECT_EMPRESAS_BASE = "SELECT e.ID_EMPRESA,e.NIT_EMPRESA,e.RAZON_SOCIAL FROM EMPRESAS e ORDER BY e.RAZON_SOCIAL";
	
	public static final String SELECT_PRODUCTOS_EMPRESA_BASE = "SELECT p.ID_PRODUCTO, p.NOMBRE FROM EMPRESAS_PRODUCTOS ep INNER JOIN PRODUCTOS p ON p.ID_PRODUCTO=ep.ID_PRODUCTO WHERE ep.ID_EMPRESA=:idEmpresa ORDER BY p.NOMBRE";

	public static final String SELECT_EMPRESAS_POR_ID_USAURIO = "SELECT em.id_empresa, em.nit_empresa, em.razon_social, em.direccion, em.telefono, em.representante_legal, em.id_empresa_padre FROM EMPRESAS em  "
			+ " WHERE em.id_empresa IN  (SELECT id_empresa FROM USUARIOS_ROLES_EMPRESAS usurolRmp WHERE usurolRmp.id_usuario = :idUSuario)";

	public static final String SELECT_PRODUCTOS_ID_EMPRESA = "SELECT p.ID_PRODUCTO, p.NOMBRE, ep.VALOR_MINIMO, ep.VALOR_MAXIMO, ep.VALOR_MAXIMO_DIA, ep.HORA_INICIO_VENTA, ep.HORA_FINAL_VENTA, ep.ID_EMPRESA  "
			+ " FROM EMPRESAS_PRODUCTOS ep INNER JOIN PRODUCTOS p ON p.ID_PRODUCTO=ep.ID_PRODUCTO WHERE ep.ID_EMPRESA = :idEmpresa AND ep.ID_ESTADO = 'ACTIVO' ORDER BY p.NOMBRE";

	public static final String SELECT_EMPRESAS_PRODUCTOS_COMISIONES_ID_EMPRESA = "SELECT epc.ID_EMPRESA, epc.ID_PRODUCTO, epc.ID_COMISION, epc.PORCENTAJE_COMISION, epc.VALOR_FIJO_COMISION, p.NOMBRE "
			+ " FROM EMPRESAS_PRODUCTOS_COMISIONES epc INNER JOIN EMPRESAS em ON em.ID_EMPRESA = epc.ID_EMPRESA INNER JOIN PRODUCTOS p on p.ID_PRODUCTO = epc.ID_PRODUCTO WHERE em.ID_EMPRESA = :idEmpresa AND p.ID_PRODUCTO = :idProducto AND epc.id_estado = 'ACTIVO' ORDER BY p.NOMBRE";

	public static final String SELECT_CUENTAS_PRODUCTOS_EMPRESA = "SELECT cp.ID_EMPRESA, cp.ID_PRODUCTO, cp.ID_CUENTA, cp.COD_CUENTA, c.NOMBRE, p.NOMBRE AS nombre_producto "
			+ " FROM CUENTAS_PRODUCTOS cp INNER JOIN EMPRESAS em ON em.ID_EMPRESA = cp.ID_EMPRESA INNER JOIN PRODUCTOS p on p.ID_PRODUCTO = cp.ID_PRODUCTO INNER JOIN CUENTAS c on c.ID_CUENTA = cp.ID_CUENTA WHERE em.ID_EMPRESA = :idEmpresa AND p.ID_PRODUCTO = :idProducto ORDER BY p.NOMBRE ";
	
	public static final String UPDATE_ASOCIACION_EMPRESAS_PRODUCTOS = "UPDATE EMPRESAS_PRODUCTOS SET VALOR_MINIMO = :valorMinimo, VALOR_MAXIMO = :valorMaximo, VALOR_MAXIMO_DIA = :valorMaximoDia, HORA_INICIO_VENTA = :horaInicioVenta, HORA_FINAL_VENTA = :horaFinalVenta WHERE ID_EMPRESA = :idEmpresa AND ID_PRODUCTO = :idProducto";

	public static final String UPDATE_ASOCIACION_EMPRESAS_PRODUCTOS_COMISIONES = "UPDATE EMPRESAS_PRODUCTOS_COMISIONES SET PORCENTAJE_COMISION = :porcentajeComision, VALOR_FIJO_COMISION = :valorFijoComision WHERE ID_EMPRESA = :idEmpresa AND ID_PRODUCTO = :idProducto AND ID_COMISION = :idComision";

	public static final String UPDATE_ASOCIACION_CUENTAS_PRODUCTOS = "UPDATE CUENTAS_PRODUCTOS SET COD_CUENTA = :codCuenta WHERE ID_EMPRESA = :idEmpresa AND ID_PRODUCTO = :idProducto AND ID_CUENTA = :idCuenta";

	public static final String INSERT_EMPRESAS_PRODUCTOS = "INSERT INTO EMPRESAS_PRODUCTOS(ID_EMPRESA, ID_PRODUCTO, VALOR_MINIMO, VALOR_MAXIMO, VALOR_MAXIMO_DIA, ID_ESTADO, HORA_INICIO_VENTA, HORA_FINAL_VENTA) "
			+ "VALUES (:idEmpresa, :idProducto, :valorMinimo, :valorMaximo, :valorMaximoDia, :idEstado, :horaInicioVenta, :horaFinalVenta)";

	public static final String INSERT_EMPRESAS_PRODUCTOS_COMISIONES = "INSERT INTO EMPRESAS_PRODUCTOS_COMISIONES(ID_EMPRESA, ID_PRODUCTO, ID_COMISION, PORCENTAJE_COMISION, VALOR_FIJO_COMISION, ID_ESTADO) VALUES (:idEmpresa, :idProducto, :idComision, :porcentajeComision, :valorFijoComision, :idEstado)";

	public static final String INSERT_CUENTAS_PRODUCTOS = "INSERT INTO CUENTAS_PRODUCTOS(ID_EMPRESA, ID_PRODUCTO, ID_CUENTA, COD_CUENTA)VALUES (:idEmpresa, :idProducto, :idCuenta, :codCuenta)";

	public static final String SELECT_COMISIONES_EDITAR_ID_EMPRESA = "SELECT epc.ID_EMPRESA, epc.ID_PRODUCTO, epc.ID_COMISION, epc.PORCENTAJE_COMISION, epc.VALOR_FIJO_COMISION, p.NOMBRE, c.NOMBRE AS NOMBRE_CUENTA "
			+ " FROM EMPRESAS_PRODUCTOS_COMISIONES epc INNER JOIN EMPRESAS em ON em.ID_EMPRESA = epc.ID_EMPRESA INNER JOIN PRODUCTOS p on p.ID_PRODUCTO = epc.ID_PRODUCTO INNER JOIN COMISIONES c on c.ID_COMISION = epc.ID_COMISION WHERE em.ID_EMPRESA = :idEmpresa AND epc.id_estado = 'ACTIVO' ORDER BY p.NOMBRE";

	public static final String SELECT_CUENTAS_EDITAR_ID_EMPRESA = "SELECT cp.ID_EMPRESA, cp.ID_PRODUCTO, cp.ID_CUENTA, cp.COD_CUENTA, c.NOMBRE, p.NOMBRE AS NOMBRE_PRODUCTO "
			+ " FROM CUENTAS_PRODUCTOS cp INNER JOIN EMPRESAS em ON em.ID_EMPRESA = cp.ID_EMPRESA INNER JOIN PRODUCTOS p on p.ID_PRODUCTO = cp.ID_PRODUCTO INNER JOIN CUENTAS c on c.ID_CUENTA = cp.ID_CUENTA WHERE em.ID_EMPRESA = :idEmpresa ORDER BY p.NOMBRE ";
	
	/** SELECT para obtener las solicitudes de calendario sorteo */
	public static final String SELECT_SOLICITUDES_CALENDARIO_SORTEO =
		"SELECT "
			+ "A.ID_AUTORIZACION AS ID_SOLICITUD,"
			+ "TO_CHAR(A.FECHA_SOLICITUD,'YYYY-MM-DD')AS FECHA_SOLICITUD,"
			+ "A.HORA_SOLICITUD AS HORA_SOLICITUD,"
			+ "TA.DESCRIPCION AS TIPO_AUTORIZACION,"
			+ "CONCAT(P.PRIMER_NOMBRE,' ',P.SEGUNDO_NOMBRE,' ',P.PRIMER_APELLIDO,' ',P.SEGUNDO_APELLIDO) AS SOLICITANTE,"
			+ "CASE "
			+ "WHEN AD.CAMPO='" + Constants.ID_SORTEO_DETALLE + "'"
			+ "	THEN(SELECT L.NOMBRE FROM SORTEOS_DETALLES SD JOIN LOTERIAS L ON(L.ID_LOTERIA=SD.ID_LOTERIA)WHERE SD.ID_SORTEO_DETALLE=AD.VALOR \\:\\:integer)"
			+ "ELSE "
			+ "	(SELECT L.NOMBRE FROM SORTEOS_DETALLES SD JOIN LOTERIAS L ON(L.ID_LOTERIA=SD.ID_LOTERIA)WHERE SD.ID_SORTEO=AD.VALOR \\:\\:integer LIMIT 1)"
			+ "END AS LOTERIA ";

	/** FROM para obtener las solicitudes de calendario sorteo */
	public static final String FROM_SOLICITUDES_CALENDARIO_SORTEO =
		"FROM AUTORIZACIONES A "
		+ "JOIN AUTORIZACIONES_DETALLES AD ON(AD.ID_AUTORIZACION=A.ID_AUTORIZACION)"
		+ "JOIN TIPOS_AUTORIZACIONES TA ON(TA.ID_TIPO_AUTORIZACION=A.ID_TIPO_AUTORIZACION)"
		+ "JOIN PERSONAS P ON(P.ID_PERSONA=A.ID_USUARIO_SOLICITANTE)"
		+ "WHERE (AD.CAMPO='" + Constants.ID_SORTEO + "' OR AD.CAMPO='" + Constants.ID_SORTEO_DETALLE + "')"
		+ "AND A.ID_ESTADO='" + EstadoEnum.PENDIENTE + "'";

	/** ORDER BY para la solicitudes de calendario sorteo */
	public static final String ORDERBY_SOLICITUDES_CALENDARIO_SORTEO = " ORDER BY A.FECHA_SOLICITUD ASC";

	/** filtro para obtener las solicitudes calendario sorteo por fecha inicio */
	public static final String FILTER_SOLICITUDES_CALENDARIO_FECHA_INICIO = " AND A.FECHA_SOLICITUD>=?";

	/** filtro para obtener las solicitudes calendario sorteo por fecha final */
	public static final String FILTER_SOLICITUDES_CALENDARIO_FECHA_FINAL = " AND A.FECHA_SOLICITUD<=?";
}
