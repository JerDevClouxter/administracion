package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Usuarios;

public interface IUsuariosRepository extends JpaRepository<Usuarios, Long>{

}
