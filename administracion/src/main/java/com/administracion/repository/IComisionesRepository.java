package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Comisiones;

/**
 * Spring data repository para la entidad de COMISIONES
 */
public interface IComisionesRepository extends JpaRepository<Comisiones, Long> {}
