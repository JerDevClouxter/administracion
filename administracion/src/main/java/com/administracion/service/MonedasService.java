package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.MonedasDTO;
import com.administracion.entity.Monedas;
import com.administracion.repository.MonedasRepository;

/**
 * @service contiene los procesos de negocio para Monedas
 */
@Service
@Transactional(readOnly = true)
public class MonedasService {

	@Autowired
	private MonedasRepository monedasRepository;
	
	public List<MonedasDTO> findAll() {
		List<MonedasDTO> listMonedasDTO = null;
		Builder<Monedas, MonedasDTO> builder = new Builder<Monedas, MonedasDTO>(MonedasDTO.class);
		List<Monedas> listMonedas = this.monedasRepository.findAll();
		if (!listMonedas.isEmpty()) {
			listMonedasDTO = builder.copy(listMonedas);
		}
		return listMonedasDTO;
	}
	
	@Transactional
	public Monedas save(MonedasDTO monedaDTO) {
		Builder<MonedasDTO, Monedas> builder = 
				new Builder<MonedasDTO, Monedas>(Monedas.class);
		return this.monedasRepository.save(builder.copy(monedaDTO));		
	}
	
}
