package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.TipoProductosDTO;
import com.administracion.entity.TipoProductos;
import com.administracion.repository.TipoProductosRepository;

/**
 * @service contiene los procesos de negocio para TipoProductos
 */
@Service
@Transactional(readOnly = true)
public class TipoProductosService {

	@Autowired
	private TipoProductosRepository tipoProductosRepository;
	
	public List<TipoProductosDTO> findAll() {
		List<TipoProductosDTO> listTipoProductosDTO = null;
		Builder<TipoProductos, TipoProductosDTO> builder = new Builder<TipoProductos, TipoProductosDTO>(TipoProductosDTO.class);
		List<TipoProductos> ListTipoProductos = this.tipoProductosRepository.findAll();
		if (!ListTipoProductos.isEmpty()) {
			listTipoProductosDTO = builder.copy(ListTipoProductos);
		}
		return listTipoProductosDTO;
	}
	
	@Transactional
	public TipoProductos save(TipoProductos tipoProductos) {
		return this.tipoProductosRepository.save(tipoProductos);
	}
	
}

