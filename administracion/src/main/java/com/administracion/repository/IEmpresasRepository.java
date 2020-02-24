package com.administracion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.administracion.entity.Empresas;

/**
 * Spring data repository para la entidad de EMPRESAS
 */
@Repository
public interface IEmpresasRepository extends JpaRepository<Empresas, Long> {
	/**
	 * Metodo encargado de obtener la empresa relacionada a un idEmpresa
	 * 
	 * @param idEmpresa
	 * @return Empresa
	 */
	public Empresas findByIdEmpresa(Long idEmpresa);
}
