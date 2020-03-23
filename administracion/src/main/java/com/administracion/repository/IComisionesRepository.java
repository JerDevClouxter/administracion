package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Comisiones;

public interface IComisionesRepository extends JpaRepository<Comisiones, Long> {}
