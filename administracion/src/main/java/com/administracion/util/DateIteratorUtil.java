package com.administracion.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * Clase utilitaria que permite iterar en un rango de fecha inicio y final
 */
public class DateIteratorUtil implements Iterator<Date>, Iterable<Date> {

	/** Es la fecha inicial a iterar */
	private Calendar start = Calendar.getInstance();

	/** Es la fecha final a iterar */
	private Calendar end = Calendar.getInstance();

	/**
	 * Constructor del iterador donde se configura la fecha inicio - final
	 */
	public DateIteratorUtil(Date start, Date end) {
		this.start.setTime(start);
		this.end.setTime(end);
	}

	/**
	 * Permite validar si ya es el ultimo dia a iterar
	 */
	public boolean hasNext() {
		return !start.after(end);
	}

	/**
	 * Se incrementa un dia mas que es el siguiente
	 */
	public Date next() {
		start.add(Calendar.DATE, 1);
		return start.getTime();
	}

	/**
	 * No se permite remover ningun data
	 */
	public void remove() {
		throw new UnsupportedOperationException("No es posible remover");
	}

	/**
	 * Permite obtener la instancia especifica del iterador
	 */
	public Iterator<Date> iterator() {
		return this;
	}
}
