package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.administracion.entity.Aplicaciones;

/**
 * Spring data repository para la entidad de APLICACIONES
 */
@Repository
public interface AplicacionesRepository extends JpaRepository<Aplicaciones, Long> {}
