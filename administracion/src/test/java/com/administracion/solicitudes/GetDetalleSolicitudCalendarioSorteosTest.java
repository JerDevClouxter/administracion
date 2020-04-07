package com.administracion.solicitudes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.administracion.dto.solicitudes.DetalleSolicitudCalendarioSorteoDTO;
import com.administracion.dto.solicitudes.SolicitudCalendarioSorteoDTO;
import com.administracion.service.SolicitudesService;

/**
 * Test para el proceso de negocio que permite consultar el detalle de la
 * solicitud para calendario sorteos
 * http://localhost:port/solicitudes/getDetalleSolicitudCalendarioSorteos
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDetalleSolicitudCalendarioSorteosTest {

	/** Service que contiene los procesos de negocio para las solicitudes */
	@Autowired
	private SolicitudesService service;

	/**
	 * Metodo test para validar el flujo basico para consultar
	 * el detalle de la solicitud para calendario sorteos
	 */
	@Test
	public void getDetalleSolicitudCalendarioSorteosFlujoBasico() {
		try {
			// se construye los datos de la solicitud
			SolicitudCalendarioSorteoDTO solicitud = new SolicitudCalendarioSorteoDTO();
			solicitud.setIdSolicitud(161l);

			// se invoca el el proceso de negocio
			DetalleSolicitudCalendarioSorteoDTO detl = this.service.getDetalleSolicitudCalendarioSorteos(solicitud);

			// debe tener algun detalle
			assertTrue(detl != null && detl.getIdSolicitud() != null);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
