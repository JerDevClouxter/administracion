package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.TiposProductoDTO;
import com.administracion.entity.TiposProductos;
import com.administracion.repository.TiposProductosRepository;

/**
 * @service contiene los procesos de negocio para TiposProductos
 */
@Service
@Transactional(readOnly = true)
public class TiposProductosService {

	@Autowired
	private TiposProductosRepository tiposProductosRepository;
	
	public List<TiposProductoDTO> findAll() {
		List<TiposProductoDTO> listTipoProductosDTO = null;
		Builder<TiposProductos, TiposProductoDTO> builder = new Builder<TiposProductos, TiposProductoDTO>(TiposProductoDTO.class);
		List<TiposProductos> ListTipoProductos = this.tiposProductosRepository.findAll();
		if (!ListTipoProductos.isEmpty()) {
			listTipoProductosDTO = builder.copy(ListTipoProductos);
		}
		return listTipoProductosDTO;
	}
	
	@Transactional
	public TiposProductos save(TiposProductos tipoProductos) {
		return this.tiposProductosRepository.save(tipoProductos);
	}
	
}

