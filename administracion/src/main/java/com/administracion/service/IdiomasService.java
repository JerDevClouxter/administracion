package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.IdiomaDTO;
import com.administracion.entity.Idiomas;
import com.administracion.repository.IdiomasRepository;

/**
 * Service que contiene los procesos de negocio para los IDIOMAS
 */
@Service
@Transactional(readOnly = true)
public class IdiomasService {
	
	/** Repository que contiene los metodos utilitarios para la persistencia de la entidad IDIOMAS */
	@Autowired
	private IdiomasRepository idiomaRepository;
	
	public List<IdiomaDTO> findAll() {
		List<IdiomaDTO> lstIdiomasDTO = null;
		Builder<Idiomas, IdiomaDTO> builder = new Builder<Idiomas, IdiomaDTO>(IdiomaDTO.class);
		List<Idiomas> lstIdiomas = this.idiomaRepository.findAll();
		if (!lstIdiomas.isEmpty()) {
			lstIdiomasDTO = builder.copy(lstIdiomas);
		}
		return lstIdiomasDTO;
	}
	
	@Transactional
	public Idiomas save(Idiomas idioma) {
		return this.idiomaRepository.save(idioma);
	}
	
}
