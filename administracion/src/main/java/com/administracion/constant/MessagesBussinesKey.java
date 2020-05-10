package com.administracion.constant;

/**
 * Se debe mandar el codigo del business messages al cliente (angular, movil)
 */
public enum MessagesBussinesKey {

	KEY_SIN_RELACION_EMPRESA_POR_IDUSUARIO("admin-0001"),
	KEY_SIN_ASOCIACION_PRODUCTOS_EMPRESA_SELECCIONADA("admin-0002"),
	KEY_SIN_ASOCIACION_COMISION_PRODUCTOS_EMPRESA("admin-0003"),
	KEY_CONFIGURACION_PRODUCTO_EMPRESA_EXISTENTE("admin-0004"),
	KEY_CONFIGURACION_COMISION_PRODUCTO_EMPRESA_EXISTENTE("admin-0005"),
	KEY_CONFIGURACION_CUENTA_PRODUCTO_EMPRESA_EXISTENTE("admin-0006"),
	KEY_SOLICITUD_DATA_REQUERIDO("admin-0007"),
	KEY_LONGITUD_NO_PERMITIDA("admin-0008"),
	KEY_NOMBRE_ROL_YA_EXISTE("admin-0009"),
	KEY_SIN_RELACION_USUARIO_TIP_NUM_DOC("admin-0020"),
	KEY_SIN_RELACION_USUARIO_ROL_EMPRESA_POR_IDUSUARIO("admin-0021");

	public final String value;

	private MessagesBussinesKey(String value) {
		this.value = value;
	}
}
