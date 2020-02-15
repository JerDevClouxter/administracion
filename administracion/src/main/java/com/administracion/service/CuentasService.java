package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.CuentaDTO;
import com.administracion.entity.Cuentas;
import com.administracion.repository.CuentasRepository;

/**
 * Service que contiene los procesos de negocio para las CUENTAS
 */
@Service
@Transactional(readOnly = true)
public class CuentasService {

	/** Repository que contiene los metodos utilitarios para la persistencia de la entidad CUENTAS */
	@Autowired
	private CuentasRepository cuentaRepository;
	
	public List<CuentaDTO> findAll() {
		List<CuentaDTO> lstCuentasDTO = null;
		Builder<Cuentas, CuentaDTO> builder = new Builder<Cuentas, CuentaDTO>(CuentaDTO.class);
		List<Cuentas> lstCuentas = this.cuentaRepository.findAll();
		if (!lstCuentas.isEmpty()) {
			lstCuentasDTO = builder.copy(lstCuentas);
		}
		return lstCuentasDTO;
	}
	
	@Transactional
	public Cuentas save(Cuentas cuenta) {
		return this.cuentaRepository.save(cuenta);
	}
	
}
