package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.TiposProductosDTO;
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
	
	public List<TiposProductosDTO> findAll() {
		List<TiposProductosDTO> listTipoProductosDTO = null;
		Builder<TiposProductos, TiposProductosDTO> builder = new Builder<TiposProductos, TiposProductosDTO>(TiposProductosDTO.class);
		List<TiposProductos> ListTipoProductos = this.tiposProductosRepository.findAll();
		if (!ListTipoProductos.isEmpty()) {
			listTipoProductosDTO = builder.copy(ListTipoProductos);
		}
		return listTipoProductosDTO;
	}
	
	@Transactional
	public TiposProductos save(TiposProductosDTO tipoProductoDTO) {
		Builder<TiposProductosDTO, TiposProductos> builder = 
				new Builder<TiposProductosDTO, TiposProductos>(TiposProductos.class);
		return this.tiposProductosRepository.save(builder.copy(tipoProductoDTO));		
	}
	
}

