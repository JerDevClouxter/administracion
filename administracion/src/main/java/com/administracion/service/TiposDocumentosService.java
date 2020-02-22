package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.TiposDocumentosDTO;
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
	
	public List<TiposDocumentosDTO> findAll() {
		List<TiposDocumentosDTO> listTipoDocumentosDTO = null;
		Builder<TiposDocumentos, TiposDocumentosDTO> builder = new Builder<TiposDocumentos, TiposDocumentosDTO>(TiposDocumentosDTO.class);
		List<TiposDocumentos> listTipoDocumentos = this.tiposDocumentosRepository.findAll();
		if (!listTipoDocumentos.isEmpty()) {
			listTipoDocumentosDTO = builder.copy(listTipoDocumentos);
		}
		return listTipoDocumentosDTO;
	}
	
	@Transactional
	public TiposDocumentos save(TiposDocumentosDTO tipoDocumentoDTO) {
		Builder<TiposDocumentosDTO, TiposDocumentos> builder = 
				new Builder<TiposDocumentosDTO, TiposDocumentos>(TiposDocumentos.class);
		return this.tiposDocumentosRepository.save(builder.copy(tipoDocumentoDTO));
	}
	
}
