package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.IdiomasDTO;
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
	private IdiomasRepository idiomasRepository;
	
	public List<IdiomasDTO> findAll() {
		List<IdiomasDTO> lstIdiomasDTO = null;
		Builder<Idiomas, IdiomasDTO> builder = new Builder<Idiomas, IdiomasDTO>(IdiomasDTO.class);
		List<Idiomas> lstIdiomas = this.idiomasRepository.findAll();
		if (!lstIdiomas.isEmpty()) {
			lstIdiomasDTO = builder.copy(lstIdiomas);
		}
		return lstIdiomasDTO;
	}
	
	@Transactional
	public Idiomas save(IdiomasDTO idiomaDTO) {
		Builder<IdiomasDTO, Idiomas> builder = 
				new Builder<IdiomasDTO, Idiomas>(Idiomas.class);
		return this.idiomasRepository.save(builder.copy(idiomaDTO));
	}
	
}
