package com.administracion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.TiposDocumentos;

public interface ITiposDocumentosRepository extends JpaRepository<TiposDocumentos, Long> {

	public List<TiposDocumentos> findAll();
}
