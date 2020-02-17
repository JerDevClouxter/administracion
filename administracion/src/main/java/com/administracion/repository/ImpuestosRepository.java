package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Impuestos;

public interface ImpuestosRepository extends JpaRepository<Impuestos, Long> {}
