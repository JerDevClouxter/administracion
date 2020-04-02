package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.administracion.entity.Cuentas;

/**
 * Spring data repository para la entidad de CUENTAS
 */
@Repository
public interface ICuentasRepository extends JpaRepository<Cuentas, Long> {}
