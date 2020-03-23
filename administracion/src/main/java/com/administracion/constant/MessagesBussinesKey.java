package com.administracion.constant;

/**
 * Se debe mandar el codigo del business messages al cliente (angular, movil)
 */
public enum MessagesBussinesKey {

	KEY_SIN_RELACION_EMPRESA_POR_IDUSUARIO("0020"),
	KEY_SIN_ASOCIACION_PRODUCTOS_EMPRESA_SELECCIONADA("0021"),
	KEY_SIN_ASOCIACION_COMISION_PRODUCTOS_EMPRESA("0022"),
	;

	public final String value;
	private MessagesBussinesKey(String value) {
		this.value = value;
	}
}
