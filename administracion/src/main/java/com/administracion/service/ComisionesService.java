package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.ComisionesDTO;
import com.administracion.entity.Comisiones;
import com.administracion.repository.ComisionesRepository;

/**
 * @service contiene los procesos de negocio para comisiones
 */
@Service
@Transactional(readOnly = true)
public class ComisionesService {

	@Autowired
	private ComisionesRepository comisionRepository;
	
	public List<ComisionesDTO> findAll() {
		List<ComisionesDTO> ComisionesDTO = null;
		Builder<Comisiones, ComisionesDTO> builder = new Builder<Comisiones, ComisionesDTO>(ComisionesDTO.class);
		List<Comisiones> listComisionDTO = this.comisionRepository.findAll();
		if (!listComisionDTO.isEmpty()) {
			ComisionesDTO = builder.copy(listComisionDTO);
		}
		return ComisionesDTO;
	}
	
	@Transactional
	public Comisiones save(Comisiones comision) {
		return this.comisionRepository.save(comision);
	}
	
}
