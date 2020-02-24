package com.administracion.util;

/**
 * Enum que contiene todos los números que se utilizaran
 *
 */
public enum Numero {

	ZERO(0, 0l);

	public final Integer valueI;
	public final Long valueL;

	private Numero(Integer valueI, Long valueL) {
		this.valueI = valueI;
		this.valueL = valueL;
	}

}
