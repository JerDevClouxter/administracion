package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.administracion.entity.Empresas;

/**
 * Spring data repository para la entidad de EMPRESAS
 */
@Repository
public interface EmpresasRepository extends JpaRepository<Empresas, Long> {}
