package com.administracion.constant;

/**
 * Se debe mandar el codigo del business messages al cliente (angular, movil)
 */
public enum MessagesBussinesKey {

	KEY_SIN_RELACION_EMPRESA_POR_IDUSUARIO("0020"),
	KEY_SIN_ASOCIACION_PRODUCTOS_EMPRESA_SELECCIONADA("0021"),
	KEY_SIN_ASOCIACION_COMISION_PRODUCTOS_EMPRESA("0022"),
	KEY_CONFIGURACION_PRODUCTO_EMPRESA_EXISTENTE("0023"),
	KEY_CONFIGURACION_COMISION_PRODUCTO_EMPRESA_EXISTENTE("0024"),
	KEY_CONFIGURACION_CUENTA_PRODUCTO_EMPRESA_EXISTENTE("0025"),
	;

	public final String value;
	private MessagesBussinesKey(String value) {
		this.value = value;
	}
}
