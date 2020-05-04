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
			+ " THEN(SELECT L.NOMBRE FROM SORTEOS_DETALLES SD JOIN LOTERIAS L ON(L.ID_LOTERIA=SD.ID_LOTERIA)WHERE SD.ID_SORTEO_DETALLE=AD.VALOR \\:\\:integer)"
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
		+ "AND A.ID_ESTADO='" + EstadoEnum.PENDIENTE + "' "
		+ "AND A.ID_TIPO_AUTORIZACION IN(" + TiposAutorizacionesConstant.CREACION_SORTEOS + "," + TiposAutorizacionesConstant.MODIFICAR_SORTEOS + "," + TiposAutorizacionesConstant.CANCELAR_SORTEOS + ")";

	/** ORDER BY para la solicitudes de calendario sorteo */
	public static final String ORDERBY_SOLICITUDES_CALENDARIO_SORTEO = " ORDER BY A.FECHA_SOLICITUD ASC";

	/** filtro para obtener las solicitudes calendario sorteo por fecha inicio */
	public static final String FILTER_SOLICITUDES_CALENDARIO_FECHA_INICIO = " AND A.FECHA_SOLICITUD>=?";

	/** filtro para obtener las solicitudes calendario sorteo por fecha final */
	public static final String FILTER_SOLICITUDES_CALENDARIO_FECHA_FINAL = " AND A.FECHA_SOLICITUD<=?";

	/** SELECT para obtener los detalles de la solicitud de calendario sorteo */
	public static final String SELECT_DETALLE_SOLICITUD_CALENDARIO_SORTEO =
		"SELECT "
			+ "A.ID_TIPO_AUTORIZACION,"
			+ "A.MOTIVO,"
			+ "AD.CAMPO,"
			+ "AD.VALOR,"
			+ "CASE WHEN AD.CAMPO='" + Constants.ID_SORTEO_DETALLE + "'"
			+ " THEN(SELECT SD.HORA_SORTEO FROM SORTEOS_DETALLES SD WHERE SD.ID_SORTEO_DETALLE=AD.VALOR \\:\\:integer)"
			+ "END AS HORA_SORTEO,"
			+ "CASE WHEN AD.CAMPO='" + Constants.ID_SORTEO_DETALLE + "'"
			+ " THEN(SELECT SD.FECHA_SORTEO FROM SORTEOS_DETALLES SD WHERE SD.ID_SORTEO_DETALLE=AD.VALOR \\:\\:integer)"
			+ "END AS FECHA_SORTEO "
		+ "FROM AUTORIZACIONES A "
		+ "JOIN AUTORIZACIONES_DETALLES AD ON(AD.ID_AUTORIZACION=A.ID_AUTORIZACION)"
		+ "WHERE A.ID_AUTORIZACION=?";

	/** SELECT para obtener la ultima modificacion de un calendario sorteo */
	public static final String SELECT_ULTIMA_MOIFICACION_CALENDARIO_SORTEO =
		"SELECT "
			+ "(SELECT AUD.VALOR FROM AUTORIZACIONES_DETALLES AUD WHERE AUD.ID_AUTORIZACION=A.ID_AUTORIZACION AND AUD.CAMPO='" + Constants.REQUEST_JSON + "')JSON "
		+ "FROM AUTORIZACIONES A "
		+ "JOIN AUTORIZACIONES_DETALLES AD ON(AD.ID_AUTORIZACION=A.ID_AUTORIZACION)"
		+ "WHERE A.ID_ESTADO='" + EstadoEnum.AUTORIZADO.name() + "' "
		+ "AND A.ID_TIPO_AUTORIZACION IN(" + TiposAutorizacionesConstant.CREACION_SORTEOS + "," + TiposAutorizacionesConstant.MODIFICAR_SORTEOS + ")"
		+ "AND AD.CAMPO='" + Constants.ID_SORTEO + "' AND AD.VALOR=? ORDER BY A.ID_AUTORIZACION DESC LIMIT 1";

	/** UPDATE para cambiar el estado de una autorizacion */
	public static final String UPDATE_ESTADO_AUTORIZACION =
		"UPDATE AUTORIZACIONES "
			+ "SET ID_ESTADO=?,"
			+ "ID_USUARIO_AUTORIZADOR=?,"
			+ "FECHA_AUTORIZACION=CURRENT_DATE "
		+ "WHERE ID_AUTORIZACION=?";

	/** UPDATE para cambiar el estado del calendario sorteo */
	public static final String UPDATE_ESTADO_CALENDARIO_SORTEO = "UPDATE SORTEOS SET ID_ESTADO=? WHERE ID_SORTEO=?";

	/** UPDATE para cambiar el estado de todos los detalles del calendario sorteo */
	public static final String UPDATE_ESTADO_DETALLES_CALENDARIO_SORTEO = "UPDATE SORTEOS_DETALLES SET ID_ESTADO=? WHERE ID_SORTEO=?";

	/** UPDATE para cambiar el estado de un detalle de la serie */
	public static final String UPDATE_ESTADO_SORTEO = "UPDATE SORTEOS_DETALLES SET ID_ESTADO=? WHERE ID_SORTEO_DETALLE=?";

	/** UPDATE para cambiar la FECHA Y HORA del sorteo */
	public static final String UPDATE_FECHA_HORA_SORTEO = "UPDATE SORTEOS_DETALLES SET FECHA_SORTEO=?,HORA_SORTEO=? WHERE ID_SORTEO_DETALLE=?";

	/** Insert para la tabla SORTEOS_DETALLES */
	public static final String INSERT_DETALLES_SORTEOS = "INSERT INTO SORTEOS_DETALLES(ID_SORTEO,FECHA_SORTEO,HORA_SORTEO,ID_LOTERIA,ID_ESTADO)VALUES(?,?,?,?,?)";

	/** SELECT para obtener los ROLES */
	public static final String SELECT_ROLES =
		"SELECT "
			+ "R.ID_ROL AS ID,"
			+ "R.NOMBRE AS NOMBRE,"
			+ "R.DESCRIPCION AS DES,"
			+ "(SELECT STRING_AGG(DISTINCT E.RAZON_SOCIAL,','ORDER BY E.RAZON_SOCIAL)FROM ROLES_EMPRESAS ROLEE JOIN EMPRESAS E ON(E.ID_EMPRESA=ROLEE.ID_EMPRESA)WHERE ROLEE.ID_ROL=R.ID_ROL)AS EMPRESAS,"
			+ "STRING_AGG(DISTINCT REC.NOMBRE,','ORDER BY REC.NOMBRE)AS RECURSOS ";

	/** FROM para obtener los ROLES */
	public static final String FROM_ROLES =
		"FROM ROLES R "
			+ "LEFT JOIN ROLES_EMPRESAS RE ON(RE.ID_ROL=R.ID_ROL)"
			+ "LEFT JOIN ROLES_RECURSOS_ACCIONES RR ON(RR.ID_ROL=R.ID_ROL)"
			+ "LEFT JOIN RECURSOS REC ON(REC.ID_RECURSO=RR.ID_RECURSO)";

	/** ORDER BY para obtener los ROLES */
	public static final String ORDER_GROUP_ROLES = "GROUP BY 1,2,3 ORDER BY R.NOMBRE";
}
