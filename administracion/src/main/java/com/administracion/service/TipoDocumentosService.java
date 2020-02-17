package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.TipoDocumentosDTO;
import com.administracion.entity.TipoDocumentos;
import com.administracion.repository.TipoDocumentosRepository;

/**
 * @service contiene los procesos de negocio para los TipoDocumentos
 */
@Service
@Transactional(readOnly = true)
public class TipoDocumentosService {

	@Autowired
	private TipoDocumentosRepository tipoDocumentosRepository;
	
	public List<TipoDocumentosDTO> findAll() {
		List<TipoDocumentosDTO> listTipoDocumentosDTO = null;
		Builder<TipoDocumentos, TipoDocumentosDTO> builder = new Builder<TipoDocumentos, TipoDocumentosDTO>(TipoDocumentosDTO.class);
		List<TipoDocumentos> listTipoDocumentos = this.tipoDocumentosRepository.findAll();
		if (!listTipoDocumentos.isEmpty()) {
			listTipoDocumentosDTO = builder.copy(listTipoDocumentos);
		}
		return listTipoDocumentosDTO;
	}
	
	@Transactional
	public TipoDocumentos save(TipoDocumentos tipoDocumentos) {
		return this.tipoDocumentosRepository.save(tipoDocumentos);
	}
	
}
