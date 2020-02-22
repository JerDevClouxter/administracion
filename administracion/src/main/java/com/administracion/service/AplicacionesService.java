package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.AplicacionesDTO;
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
	
	public List<AplicacionesDTO> findAll() {
		List<AplicacionesDTO> lstAplicacionesDTO = null;
		Builder<Aplicaciones, AplicacionesDTO> builder = new Builder<Aplicaciones, AplicacionesDTO>(AplicacionesDTO.class);
		List<Aplicaciones> lstAplicaciones = this.aplicacionRepository.findAll();
		
		if (!lstAplicaciones.isEmpty()) {
			lstAplicacionesDTO = builder.copy(lstAplicaciones);
		}
		
		return lstAplicacionesDTO;
	}
	
	@Transactional
	public Aplicaciones save(AplicacionesDTO aplicacionDTO) {
		Builder<AplicacionesDTO, Aplicaciones> builder = 
				new Builder<AplicacionesDTO, Aplicaciones>(Aplicaciones.class);
		return this.aplicacionRepository.save(builder.copy(aplicacionDTO));
	}
	
}
