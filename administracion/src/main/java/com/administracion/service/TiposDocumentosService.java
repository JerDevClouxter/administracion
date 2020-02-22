package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.TiposDocumentoDTO;
import com.administracion.entity.TiposDocumentos;
import com.administracion.repository.TiposDocumentosRepository;

/**
 * @service contiene los procesos de negocio para los TiposDocumentos
 */
@Service
@Transactional(readOnly = true)
public class TiposDocumentosService {

	@Autowired
	private TiposDocumentosRepository tiposDocumentosRepository;
	
	public List<TiposDocumentoDTO> findAll() {
		List<TiposDocumentoDTO> listTipoDocumentosDTO = null;
		Builder<TiposDocumentos, TiposDocumentoDTO> builder = new Builder<TiposDocumentos, TiposDocumentoDTO>(TiposDocumentoDTO.class);
		List<TiposDocumentos> listTipoDocumentos = this.tiposDocumentosRepository.findAll();
		if (!listTipoDocumentos.isEmpty()) {
			listTipoDocumentosDTO = builder.copy(listTipoDocumentos);
		}
		return listTipoDocumentosDTO;
	}
	
	@Transactional
	public TiposDocumentos save(TiposDocumentos tipoDocumentos) {
		return this.tiposDocumentosRepository.save(tipoDocumentos);
	}
	
}
