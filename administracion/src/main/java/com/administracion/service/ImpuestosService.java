package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.ImpuestosDTO;
import com.administracion.entity.Impuestos;
import com.administracion.repository.ImpuestosRepository;

/**
 * @service contiene los procesos de negocio para impuestos
 */
@Service
@Transactional(readOnly = true)
public class ImpuestosService {

	@Autowired
	private ImpuestosRepository impuestoRepository;
	
	public List<ImpuestosDTO> findAll() {
		List<ImpuestosDTO> listImpuestosDTO = null;
		Builder<Impuestos, ImpuestosDTO> builder = new Builder<Impuestos, ImpuestosDTO>(ImpuestosDTO.class);
		List<Impuestos> listImpuestos = this.impuestoRepository.findAll();
		if (!listImpuestos.isEmpty()) {
			listImpuestosDTO = builder.copy(listImpuestos);
		}
		return listImpuestosDTO;
	}
	
	@Transactional
	public Impuestos save(ImpuestosDTO impuestoDTO) {
		Builder<ImpuestosDTO, Impuestos> builder = 
				new Builder<ImpuestosDTO, Impuestos>(Impuestos.class);
		return this.impuestoRepository.save(builder.copy(impuestoDTO));		
	}
	
}
