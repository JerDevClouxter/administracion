package com.administracion.solicitudes;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.dto.transversal.PaginadorDTO;
import com.administracion.dto.transversal.PaginadorResponseDTO;
import com.administracion.service.SolicitudesService;

/**
 * Test para el proceso de negocio para obtener las solicitudes pendientes para
 * los calendarios de los sorteos
 * http://localhost:port/solicitudes/getSolicitudesCalendarioSorteos
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetSolicitudesCalendarioSorteosTest {

	/** Service que contiene los procesos de negocio para las solicitudes */
	@Autowired
	private SolicitudesService service;

	/**
	 * Metodo test para validar el flujo basico para obtener las
	 * solicitudes pendientes de los calendarios de los sorteos
	 */
	@Test
	public void getSolicitudesCalendarioSorteosSinFiltro() {
		try {
			// se construye el filtro de busqueda sin ningun valor
			FiltroBusquedaDTO filtro = new FiltroBusquedaDTO();

			// se configura el paginador se consulta los primeros 5
			PaginadorDTO paginador = new PaginadorDTO();
			paginador.setSkip("0");
			paginador.setRowsPage("5");
			filtro.setPaginador(paginador);

			// se invoca el el proceso de negocio
			PaginadorResponseDTO response = this.service.getSolicitudesCalendarioSorteos(filtro);

			// debe contener solicitudes
			assertTrue(response != null && response.getRegistros() != null && !response.getRegistros().isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}

	/**
	 * Metodo test para validar el flujo alterno para obtener las
	 * solicitudes pendientes de los calendarios de los sorteos
	 * con filtro de busqueda fecha inicio y final
	 */
	@Test
	public void getSolicitudesCalendarioSorteosFechas() {
		try {
			// se construye el filtro de busqueda sin ningun valor
			FiltroBusquedaDTO filtro = new FiltroBusquedaDTO();

			// se configura el paginador se consulta los primeros 5
			PaginadorDTO paginador = new PaginadorDTO();
			paginador.setSkip("0");
			paginador.setRowsPage("5");
			filtro.setPaginador(paginador);

			// filtro fecha inicio y final
			Date fechaInicio = new Date();
			filtro.setFechaInicio(fechaInicio);
			Date fechaFinal = new Date();
			filtro.setFechaFinal(fechaFinal);

			// se invoca el el proceso de negocio
			PaginadorResponseDTO response = this.service.getSolicitudesCalendarioSorteos(filtro);

			// debe contener solicitudes
			assertTrue(response != null && response.getRegistros() != null && !response.getRegistros().isEmpty());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			assertTrue(false);
		}
	}
}
