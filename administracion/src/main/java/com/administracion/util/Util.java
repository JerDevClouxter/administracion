package com.administracion.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.administracion.dto.transversal.MessageResponseDTO;
import com.administracion.enums.Numero;

/**
 * Clase que proporciona metodos utilitarios
 */
public class Util {

	/**
	 * Metodo que permite construir el response de respuesta exitoso
	 */
	public static ResponseEntity<Object> getResponseSuccessful(Object body) {
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}

	/**
	 * Metodo que permite construir el response de respuesta OK
	 */
	public static ResponseEntity<Object> getResponseOk() {
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDTO(HttpStatus.OK.getReasonPhrase()));
	}

	/**
	 * Metodo que permite construir el response de respuesta BAD REQUEST
	 */
	public static ResponseEntity<Object> getResponseBadRequest(String bussinesMessage) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO(bussinesMessage));
	}

	/**
	 * Metodo que permite construir el response de respuesta INTERNAL_SERVER_ERROR
	 * 
	 * @param metodo, metodo donde se origino el error
	 * @param error,  mensaje de la exception lanzada
	 */
	public static ResponseEntity<Object> getResponseError(String metodo, String error) {
		if (error == null || error.trim().length() == Numero.ZERO.valueI.intValue()) {
			error = "Exception lanzada por NullPointerException.";
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponseDTO(metodo + error));
	}

	/**
	 * Metodo que permite obtener un valor de un array de objecto
	 */
	public static String getValue(Object[] data, Integer index) {
		return data[index] != null ? data[index].toString() : null;
	}

	/**
	 * Metodo que permite configurar los parameters a un query a partir de una lista
	 * @param query, consulta a configurar los parametros
	 * @param parametros, lista de valores de parametros a configurar
	 */
	public static void setParameters(Query query, ArrayList<Object> parametros) {
		if (parametros != null && !parametros.isEmpty()) {
			int index = Numero.UNO.valueI;
			for(Object parametro : parametros) {
				query.setParameter(index, parametro);
				index++;
			}
		}
	}

	/**
	 * Permite limpiar la hora minuto segundo milisegundo de una fecha
	 */
	public static Date removeTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}
