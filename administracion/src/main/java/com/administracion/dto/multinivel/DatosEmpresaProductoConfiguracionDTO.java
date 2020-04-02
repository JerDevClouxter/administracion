package com.administracion.dto.multinivel;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DatosEmpresaProductoConfiguracionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<EmpresasProductosDTO> productosConfEmpresa;
	private List<EmpresasProductosComisionesDTO> comisionesConfEmpPro;
	private List<CuentasProductosDTO> cuentasConfigEmpPro;
}
