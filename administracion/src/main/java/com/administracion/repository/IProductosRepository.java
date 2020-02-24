package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Productos;


/**
 *  Repository para la entidad de Productos
 *
 */
public interface IProductosRepository extends JpaRepository<Productos, Long> {

}
