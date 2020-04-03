package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Productos;

/**
 * Spring data repository para la entidad de PRODUCTOS
 */
public interface IProductosRepository extends JpaRepository<Productos, Long> {}
