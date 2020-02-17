package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.AplicacionDTO;
import com.administracion.entity.Aplicaciones;
import com.administracion.repository.AplicacionesRepository;

/**
 * Service que contiene los procesos de negocio para las APLICACIONES
 */
@Service
@Transactional(readOnly = true)
public class AplicacionesService {

	/** Repository que contiene los metodos utilitarios para la persistencia de la entidad APLICACIONES */
	@Autowired
	private AplicacionesRepository aplicacionRepository;
	
	public List<AplicacionDTO> findAll() {
		List<AplicacionDTO> lstAplicacionesDTO = null;
		Builder<Aplicaciones, AplicacionDTO> builder = new Builder<Aplicaciones, AplicacionDTO>(AplicacionDTO.class);
		List<Aplicaciones> lstAplicaciones = this.aplicacionRepository.findAll();
		if (!lstAplicaciones.isEmpty()) {
			lstAplicacionesDTO = builder.copy(lstAplicaciones);
		}
		return lstAplicacionesDTO;
	}
	
	@Transactional
	public Aplicaciones save(Aplicaciones aplicacion) {
		return this.aplicacionRepository.save(aplicacion);
	}
	
}
