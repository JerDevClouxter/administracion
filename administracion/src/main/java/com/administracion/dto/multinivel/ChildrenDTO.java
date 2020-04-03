package com.administracion.dto.multinivel;

import java.io.Serializable;

import com.administracion.dto.transversal.EmpresasDTO;

import lombok.Data;

@Data
public class ChildrenDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private EmpresasDTO data;
}
