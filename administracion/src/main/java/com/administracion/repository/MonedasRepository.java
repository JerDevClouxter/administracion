package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Monedas;

public interface MonedasRepository extends JpaRepository <Monedas, Long> {}
