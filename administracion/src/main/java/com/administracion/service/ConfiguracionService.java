package com.administracion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administracion.builder.Builder;
import com.administracion.constant.MessagesBussinesKey;
import com.administracion.constant.SQLConstant;
import com.administracion.dto.configurar.ConfiguracionUsuarioDTO;
import com.administracion.dto.configurar.EmpresasDTO;
import com.administracion.dto.configurar.PersonasDTO;
import com.administracion.dto.configurar.RolesDTO;
import com.administracion.dto.configurar.TiposDocumentosDTO;
import com.administracion.dto.configurar.UsuariosDTO;
import com.administracion.dto.configurar.UsuariosRolesEmpresasDTO;
import com.administracion.entity.Empresas;
import com.administracion.entity.Personas;
import com.administracion.entity.Roles;
import com.administracion.entity.TiposDocumentos;
import com.administracion.entity.Usuarios;
import com.administracion.enums.EstadoEnum;
import com.administracion.enums.Numero;
import com.administracion.repository.IEmpresasRepository;
import com.administracion.repository.IPersonasRepository;
import com.administracion.repository.IRolesRepository;
import com.administracion.repository.ITiposDocumentosRepository;
import com.administracion.repository.IUsuariosRepository;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

/**
 * Service que contiene los procesos de negocio para las Configuraciones basicas
 */
@Service
public class ConfiguracionService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad ROLES
	 */
	@Autowired
	private IRolesRepository rolRepository;

	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad TIPOS_DOCUMENTOS
	 */
	@Autowired
	private ITiposDocumentosRepository tipoDocumentoRepository;

	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad PERSONAS
	 */
	@Autowired
	private IPersonasRepository personaRepository;

	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad USUARIOS
	 */
	@Autowired
	private IUsuariosRepository usuarioRepository;

	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad EMPRESAS
	 */
	@Autowired
	private IEmpresasRepository empresaRepository;

	/**
	 * Metodo encargado de consultar los tipos de documentos
	 * 
	 * @return List<TiposDocumentosDTO> lista con los tipos de documentos activos en
	 *         el sistema
	 */
	public List<TiposDocumentosDTO> consultarTiposDocumentos() throws BusinessException {
		List<TiposDocumentosDTO> listTipDocumentosDTO = null;
		try {
			Builder<TiposDocumentos, TiposDocumentosDTO> builder = new Builder<>(TiposDocumentosDTO.class);
			List<TiposDocumentos> listTiposDocumentos = tipoDocumentoRepository.findAll();
			if (!listTiposDocumentos.isEmpty()) {
				listTipDocumentosDTO = builder.copy(listTiposDocumentos);
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return listTipDocumentosDTO;
	}

	/**
	 * Metodo encargado de consultar los roles
	 * 
	 * @return List<TiposDocumentosDTO> lista con los roles activos en el sistema
	 */
	public List<RolesDTO> consultarRoles() throws BusinessException {
		List<RolesDTO> listRolesDTO = null;
		try {
			Builder<Roles, RolesDTO> builder = new Builder<>(RolesDTO.class);
			List<Roles> listRoles = rolRepository.findAll();
			if (!listRoles.isEmpty()) {
				listRolesDTO = builder.copy(listRoles);
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return listRolesDTO;
	}

	/**
	 * Metodo para consultar el rol por id
	 * 
	 * @param idRol
	 * @return RolesDTO, retorna el rol asociado al id enviado
	 * @throws BusinessException
	 */
	public RolesDTO consultarRolById(Long idRol) throws BusinessException {
		RolesDTO rolDTO = null;
		try {
			Builder<Roles, RolesDTO> builder = new Builder<>(RolesDTO.class);
			Optional<Roles> rol = rolRepository.findById(idRol);
			if (rol.isPresent()) {
				rolDTO = builder.copy(rol.get());
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return rolDTO;
	}

	/**
	 * Metodo para consultar empresas por id
	 * 
	 * @param idRol
	 * @return EmpresasDTO, retorna la empresa asociado al id enviado
	 * @throws BusinessException
	 */
	public EmpresasDTO consultarEmpresaById(Long idEmpresa) throws BusinessException {
		EmpresasDTO empresaDTO = null;
		try {
			Builder<Empresas, EmpresasDTO> builder = new Builder<>(EmpresasDTO.class);
			Optional<Empresas> empresa = empresaRepository.findById(idEmpresa);
			if (empresa.isPresent()) {
				empresaDTO = builder.copy(empresa.get());
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return empresaDTO;
	}

	/**
	 * Metodo que permite consultar por tipo y numero de documento si un usuario
	 * esta registrado y activo en el sistema
	 * 
	 * @param tipoDocumento
	 * @param numeroDocumento
	 * @return ConfiguracionUsuarioDTO
	 * @throws BusinessException
	 */
	public ConfiguracionUsuarioDTO consultarUsuarioTipDocNum(String tipoDocumento, String numeroDocumento)
			throws BusinessException {
		ConfiguracionUsuarioDTO configUsuarioDTO = new ConfiguracionUsuarioDTO();

		try {
		Long idUsuario = null;
		Query q = em.createNativeQuery(SQLConstant.SELECT_CONSULTAR_USUARIO_TIP_NUM_DOC)
				.setParameter("tipoDocumento", tipoDocumento).setParameter("numeroDocumento", numeroDocumento);
		List<Object[]> listaT = q.getResultList();
		if (listaT != null && !listaT.isEmpty()) {
			for (Object[] data : listaT) {
				PersonasDTO personaDTO = new PersonasDTO();
				idUsuario = Long.valueOf(Util.getValue(data, Numero.ZERO.valueI));
				personaDTO.setIdPersona(idUsuario);
				personaDTO.setIdTipoDocumento(Util.getValue(data, Numero.UNO.valueI));
				personaDTO.setNumeroDocumento(Util.getValue(data, Numero.DOS.valueI));
				personaDTO.setPrimerNombre(Util.getValue(data, Numero.TRES.valueI));
				personaDTO.setSegundoNombre(Util.getValue(data, Numero.CUATRO.valueI));
				personaDTO.setPrimerApellido(Util.getValue(data, Numero.CINCO.valueI));
				personaDTO.setSegundoApellido(Util.getValue(data, Numero.SEIS.valueI));
				personaDTO.setDireccion(Util.getValue(data, Numero.SIETE.valueI));
				personaDTO.setTelefono(Util.getValue(data, Numero.OCHO.valueI));
				personaDTO.setCelular(Util.getValue(data, Numero.NUEVE.valueI));
				personaDTO.setCorreoElectronico(Util.getValue(data, Numero.DIEZ.valueI));
				personaDTO.setEstrato(Long.valueOf(Util.getValue(data, Numero.ONCE.valueI)));

				UsuariosDTO usuarioDTO = new UsuariosDTO();
				usuarioDTO.setNombreUsuario(Util.getValue(data, Numero.DOCE.valueI));
				usuarioDTO.setClave(Util.getValue(data, Numero.TRECE.valueI));
				usuarioDTO.setIdEstado(Util.getValue(data, Numero.CATORCE.valueI));

				configUsuarioDTO.setPersonasDTO(personaDTO);
				configUsuarioDTO.setUsuariosDTO(usuarioDTO);
			}

			// Se consultan los roles empresa usuario asignados al usuario encontrado en la
			// busqueda
			List<UsuariosRolesEmpresasDTO> listUsuRolesEmpresasDTO = consultarUsuarioRolesIdUsuario(idUsuario);
			if (listUsuRolesEmpresasDTO != null && !listUsuRolesEmpresasDTO.isEmpty()) {
				// Se arman las listas de roles y empresas asociadas al usuario
				List<RolesDTO> rolesUsuarioDTO = new ArrayList<>();
				List<EmpresasDTO> empresasUsuarioDTO = new ArrayList<>();
				for (UsuariosRolesEmpresasDTO usuaRolEmpresasDTO : listUsuRolesEmpresasDTO) {
					rolesUsuarioDTO.add(consultarRolById(usuaRolEmpresasDTO.getIdRol()));
					empresasUsuarioDTO.add(consultarEmpresaById(usuaRolEmpresasDTO.getIdEmpresa()));
				}
				configUsuarioDTO.setListRolesDTO(rolesUsuarioDTO);
				configUsuarioDTO.setListEmpresasDTO(empresasUsuarioDTO);
			} else {
				throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_USUARIO_TIP_NUM_DOC.value);
			}

		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_USUARIO_TIP_NUM_DOC.value);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return configUsuarioDTO;
	}

	/**
	 * Metodo encargado de consultar las empresas relacionadas a el idUsuario
	 * 
	 * @param idUSuario
	 * @return List<EmpresasIdUsuarioDTO>
	 * @throws BusinessException
	 */
	public List<EmpresasDTO> consultarEmpresasIdUsuario(Long idUSuario) throws BusinessException {
		Query q = em.createNativeQuery(SQLConstant.SELECT_EMPRESAS_POR_ID_USAURIO).setParameter("idUSuario", idUSuario);
		List<Object[]> result = q.getResultList();
		EmpresasDTO empresaDTO = null;
		List<EmpresasDTO> listEmpUsuarioDTO = new ArrayList<>();
		if (result != null && !result.isEmpty()) {
			for (Object[] data : result) {
				empresaDTO = new EmpresasDTO();

				empresaDTO.setIdEmpresa(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
				empresaDTO.setNitEmpresa(Util.getValue(data, Numero.UNO.valueI));
				empresaDTO.setRazonSocial(Util.getValue(data, Numero.DOS.valueI));
				empresaDTO.setIdEmpresaPadre(Long.valueOf(Util.getValue(data, Numero.SEIS.valueI)));
				listEmpUsuarioDTO.add(empresaDTO);

			}

		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_EMPRESA_POR_IDUSUARIO.value);
		}

		return listEmpUsuarioDTO;
	}

	/**
	 * Metodo encargado de consultar los usuarios roles empresa relacionados a el
	 * idUsuario
	 * 
	 * @param idUSuario
	 * @return List<UsuariosRolesEmpresasDTO> lista de relacion entre usuarios roles
	 *         empresa
	 * @throws BusinessException
	 */
	public List<UsuariosRolesEmpresasDTO> consultarUsuarioRolesIdUsuario(Long idUSuario) throws BusinessException {
		Query q = em.createNativeQuery(SQLConstant.SELECT_USUARIO_ROL_EMPRESAS_ID_USU)
				.setParameter("idUSuario", idUSuario).setParameter("idEstado", EstadoEnum.ACTIVO.name());
		List<Object[]> result = q.getResultList();
		List<UsuariosRolesEmpresasDTO> listUsuRolesEmpresasDTO = new ArrayList<>();
		if (result != null && !result.isEmpty()) {
			for (Object[] data : result) {
				UsuariosRolesEmpresasDTO usuRolesEmpresasDTO = new UsuariosRolesEmpresasDTO();
				usuRolesEmpresasDTO.setIdRol(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
				usuRolesEmpresasDTO.setIdEmpresa(Long.valueOf(Util.getValue(data, Numero.UNO.valueI)));
				usuRolesEmpresasDTO.setIdUsuario(Long.valueOf(Util.getValue(data, Numero.DOS.valueI)));
				usuRolesEmpresasDTO.setIdEstado(Util.getValue(data, Numero.TRES.valueI));

				listUsuRolesEmpresasDTO.add(usuRolesEmpresasDTO);

			}

		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_USUARIO_ROL_EMPRESA_POR_IDUSUARIO.value);
		}

		return listUsuRolesEmpresasDTO;
	}

	/**
	 * Metodo encargado de crear un usuario en el sistema
	 * 
	 * @param configuracionUsuarioDTO contiene toda la información necesaria crear
	 *                                un usuario en el sistema
	 * 
	 * @throws BusinessException
	 */
	@Transactional
	public void crearEditarUsuario(ConfiguracionUsuarioDTO configuracionUsuarioDTO) throws BusinessException {
		try {
			Builder<UsuariosDTO, Usuarios> builderUsuario = new Builder<>(Usuarios.class);
			Builder<PersonasDTO, Personas> builderPersona = new Builder<>(Personas.class);
			usuarioRepository.save(builderUsuario.copy(configuracionUsuarioDTO.getUsuariosDTO()));
			personaRepository.save(builderPersona.copy(configuracionUsuarioDTO.getPersonasDTO()));

			// Se crear relacion en tabla roles empresa y despues en usuarios roles
			// empresa
			for (UsuariosRolesEmpresasDTO usuariosRolesEmpresa : configuracionUsuarioDTO
					.getListUsuariosRolesEmpresasDTO()) {

				if (configuracionUsuarioDTO.getUsuariosDTO().getIdEstado() != null) {
					em.createNativeQuery(SQLConstant.UPDATE_ROLES_EMPRESAS)
							.setParameter("idRol", usuariosRolesEmpresa.getIdRol())
							.setParameter("idEmpresa", usuariosRolesEmpresa.getIdEmpresa())
							.setParameter("idEstado", EstadoEnum.ACTIVO.name()).executeUpdate();

					em.createNativeQuery(SQLConstant.UPDATE_USUARIOS_ROLES_EMPRESAS)
							.setParameter("idUsuario", usuariosRolesEmpresa.getIdUsuario())
							.setParameter("idRol", usuariosRolesEmpresa.getIdRol())
							.setParameter("idEmpresa", usuariosRolesEmpresa.getIdEmpresa())
							.setParameter("idEstado", EstadoEnum.ACTIVO.name()).executeUpdate();
				} else {
					em.createNativeQuery(SQLConstant.INSERT_ROLES_EMPRESAS)
							.setParameter("idRol", usuariosRolesEmpresa.getIdRol())
							.setParameter("idEmpresa", usuariosRolesEmpresa.getIdEmpresa())
							.setParameter("idEstado", EstadoEnum.ACTIVO.name()).executeUpdate();

					em.createNativeQuery(SQLConstant.INSERT_USUARIOS_ROLES_EMPRESAS)
							.setParameter("idUsuario", usuariosRolesEmpresa.getIdUsuario())
							.setParameter("idRol", usuariosRolesEmpresa.getIdRol())
							.setParameter("idEmpresa", usuariosRolesEmpresa.getIdEmpresa())
							.setParameter("idEstado", EstadoEnum.ACTIVO.name()).executeUpdate();
				}
			}

		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

}