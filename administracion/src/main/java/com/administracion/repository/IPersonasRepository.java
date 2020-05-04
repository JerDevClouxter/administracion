package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Personas;

public interface IPersonasRepository extends JpaRepository<Personas, Long> {

	public Personas findByNumeroDocumento(String numeroDocumento);
	
}
