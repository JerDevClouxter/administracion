package com.administracion.dto.configurar;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ConfiguracionUsuarioDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PersonasDTO personasDTO;
	private UsuariosDTO usuariosDTO;
	private List<UsuariosRolesEmpresasDTO> listUsuariosRolesEmpresasDTO;
	private List<RolesDTO> listRolesDTO;
	private List<EmpresasDTO> listEmpresasDTO;

}
