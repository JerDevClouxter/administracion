package com.administracion.solicitudes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.administracion.dto.solicitudes.DetalleSolicitudCalendarioSorteoDTO;
import com.administracion.service.SolicitudesService;

/**
 * Test para el proceso de negocio que permite rechazar una solicitud de
 * calendario sorteos
 * http://localhost:port/solicitudes/rechazarSolicitudCalendarioSorteos
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RechazarSolicitudCalendarioSorteosTest {

	/** Service que contiene los procesos de negocio para las solicitudes */
	@Autowired
	private SolicitudesService service;

	/**
	 * Metodo test para validar el flujo basico de rechazar una solicitud de
	 * calendario sorteos
	 */
	@Test
	public void rechazarSolicitudCalendarioSorteosFlujoBasico() {
		try {
			// se construye el DTO con los datos de la solicitud
			DetalleSolicitudCalendarioSorteoDTO solicitud = new DetalleSolicitudCalendarioSorteoDTO();
			solicitud.setIdSolicitud(206l);
			solicitud.setIdUsuarioAutoriza(1l);
			solicitud.setIdSerieDetalle(107l);
			solicitud.setEsSolicitudCreacion(true);

			// se procede a rechazar esta solicitud
			this.service.rechazarSolicitudCalendarioSorteos(solicitud);

			// si llega a este punto es porque todo fue OK
			assertTrue(true);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
