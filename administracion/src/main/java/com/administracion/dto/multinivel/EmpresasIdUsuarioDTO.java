package com.administracion.dto.multinivel;

import java.io.Serializable;
import java.util.List;

import com.administracion.dto.EmpresasDTO;

import lombok.Data;

/**
 * DTO utilizado para almacenar las empresas relacionadas a un idUsuario
 *
 */
@Data
public class EmpresasIdUsuarioDTO  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private EmpresasDTO data;
	private List<ChildrenDTO> children;

}
