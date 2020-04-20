package com.administracion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracion.entity.Roles;

public interface IRolesRepository extends JpaRepository<Roles, Long> {

	public List<Roles> findAll();
}
