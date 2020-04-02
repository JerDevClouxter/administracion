package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.ComisionesDTO;
import com.administracion.entity.Comisiones;
import com.administracion.repository.IComisionesRepository;

/**
 * @service contiene los procesos de negocio para comisiones
 */
@Service
@Transactional(readOnly = true)
public class ComisionesService {

	@Autowired
	private IComisionesRepository comisionRepository;
	
	public List<ComisionesDTO> findAll() {
		List<ComisionesDTO> listComisionesDTO = null;
		Builder<Comisiones, ComisionesDTO> builder = new Builder<Comisiones, ComisionesDTO>(ComisionesDTO.class);
		List<Comisiones> listComision = this.comisionRepository.findAll();
		
		if (!listComision.isEmpty()) {
			listComisionesDTO = builder.copy(listComision);
		}
		
		return listComisionesDTO;
	}
	
	@Transactional
	public Comisiones save(ComisionesDTO comisionDTO) {
		Builder<ComisionesDTO, Comisiones> builder = 
				new Builder<ComisionesDTO, Comisiones>(Comisiones.class);
		return this.comisionRepository.save(builder.copy(comisionDTO));		
	}
	
}
