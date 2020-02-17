package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.administracion.entity.Idiomas;

/**
 * Spring data repository para la entidad de IDIOMAS
 */
@Repository
public interface IdiomasRepository extends JpaRepository<Idiomas, Long> {}
