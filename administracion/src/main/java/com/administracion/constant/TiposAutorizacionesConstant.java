package com.administracion.constant;

import com.administracion.enums.Numero;

/**
 * Clase constante que contiene los indentificadores de los tipos de
 * autorizaciones parametrizadas en la tabla TIPOS_AUTORIZACIONES
 */
public class TiposAutorizacionesConstant {

	/** Tipo de autorizacion para la creacion del calendario sorteos */
	public static final Integer CREACION_SORTEOS = Numero.UNO.valueI;

	/** Tipo de autorizacion para la modificacion del calendario sorteos */
	public static final Integer MODIFICAR_SORTEOS = Numero.DOS.valueI;

	/** Tipo de autorizacion para la cancelacion del calendario sorteos */
	public static final Integer CANCELAR_SORTEOS = Numero.TRES.valueI;

	/** Tipo de autorizacion para la creacion de loterias */
	public static final Integer CREAR_LOTERIAS = Numero.CUATRO.valueI;

	/** Tipo de autorizacion para la modificacion de loterias */
	public static final Integer MODIFICAR_LOTERIAS = Numero.CINCO.valueI;

	/** Tipo de autorizacion para la modificacion del numero ganador */
	public static final Integer MODIFICAR_NUMERO_GANADOR = Numero.SEIS.valueI;
}
