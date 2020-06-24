package com.administracion.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.administracion.builder.Builder;
import com.administracion.constant.MessagesBussinesKey;
import com.administracion.constant.SQLConstant;
import com.administracion.constant.SQLTransversal;
import com.administracion.dto.configurar.ConfiguracionUsuarioDTO;
import com.administracion.dto.configurar.EmpresasDTO;
import com.administracion.dto.configurar.PersonasDTO;
import com.administracion.dto.configurar.RolesDTO;
import com.administracion.dto.configurar.TiposDocumentosDTO;
import com.administracion.dto.configurar.UsuariosDTO;
import com.administracion.dto.configurar.UsuariosRolesEmpresasDTO;
import com.administracion.dto.recursos.RecursoDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.dto.transversal.PaginadorDTO;
import com.administracion.dto.transversal.PaginadorResponseDTO;
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

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

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
	public ConfiguracionUsuarioDTO consultarUsuarioTipDocNum1(String tipoDocumento, String numeroDocumento)
			throws BusinessException {
		ConfiguracionUsuarioDTO configUsuarioDTO = new ConfiguracionUsuarioDTO();

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
				personaDTO.setEstrato(Util.getValue(data, Numero.ONCE.valueI) != null
						? Long.valueOf(Util.getValue(data, Numero.ONCE.valueI))
						: null);

				UsuariosDTO usuarioDTO = new UsuariosDTO();
				usuarioDTO.setIdUsuario(idUsuario);
				usuarioDTO.setNombreUsuario(Util.getValue(data, Numero.DOCE.valueI));
				usuarioDTO.setClave(Util.getValue(data, Numero.TRECE.valueI));
				usuarioDTO.setIdEstado(Util.getValue(data, Numero.CATORCE.valueI));

				configUsuarioDTO.setPersonasDTO(personaDTO);
				configUsuarioDTO.setUsuariosDTO(usuarioDTO);
			}
			List<EmpresasDTO> empresasRolesUsuario = consultarEmpresasRoles(idUsuario);
			// Se consultan los roles empresa usuario asignados al usuario encontrado en la
			// busqueda
			if (empresasRolesUsuario != null && !empresasRolesUsuario.isEmpty()) {
				configUsuarioDTO.setListEmpresasDTO(empresasRolesUsuario);
			} else {
				throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_USUARIO_TIP_NUM_DOC.value);
			}

		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_USUARIO_TIP_NUM_DOC.value);
		}

		return configUsuarioDTO;
	}

	public PaginadorResponseDTO consultarUsuarioTipDocNum(FiltroBusquedaDTO filtro) throws Exception {

		
		// se utiliza para encapsular la respuesta de esta peticion
		PaginadorResponseDTO response = new PaginadorResponseDTO();

		// se obtiene el from de la consulta
		// StringBuilder sql1 = new StringBuilder(SQLConstant.SQL_GET_RECURSOS);
		StringBuilder sql = new StringBuilder(SQLConstant.SELECT_CONSULTAR_USUARIO_TIP_NUM_DOC);

		// contiene los valores de los parametros del los filtros
		ArrayList<Object> parametros = new ArrayList<>();

		// se configura los filtros de busqueda ingresados
		setFiltrosBusquedaUsuarios(sql, parametros, filtro);

		// se ordena la consulta
		sql.append(SQLConstant.ORDER_USUARIOS);

		// se utiliza para obtener los datos del paginador
		PaginadorDTO paginador = filtro.getPaginador();

		// se valida si se debe contar el total de los registros
		response.setCantidadTotal(paginador.getCantidadTotal());
		if (response.getCantidadTotal() == null) {

			// se configura el query con los valores de los filtros
			Query qcount = this.em.createNativeQuery(SQLTransversal.getSQLCountTbl(sql.toString()));
			Util.setParameters(qcount, parametros);

			// se configura la cantidad total de acuerdo al filtro de busqueda
			response.setCantidadTotal(((BigInteger) qcount.getSingleResult()).longValue());
		}

		// solo se consultan los registros solo si existen de acuerdo al filtro
		if (response.getCantidadTotal() != null && !response.getCantidadTotal().equals(Numero.ZERO.valueL)) {

			// se configura la paginacion de la consulta
			SQLTransversal.getSQLPaginator(paginador.getSkip(), paginador.getRowsPage(), sql);

			// se configura el query con los valores de los filtros y se obtiene los datos
			Query query = this.em.createNativeQuery(sql.toString());
			Util.setParameters(query, parametros);
			List<Object[]> result = query.getResultList();
			for (Object[] data : result) {
				ConfiguracionUsuarioDTO configUsuarioDTO = new ConfiguracionUsuarioDTO();
				PersonasDTO personaDTO = new PersonasDTO();
				personaDTO.setIdPersona(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
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
				personaDTO.setEstrato(Util.getValue(data, Numero.ONCE.valueI) != null
						? Long.valueOf(Util.getValue(data, Numero.ONCE.valueI))
						: null);

				UsuariosDTO usuarioDTO = new UsuariosDTO();
				usuarioDTO.setIdUsuario(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
				usuarioDTO.setNombreUsuario(Util.getValue(data, Numero.DOCE.valueI));
				usuarioDTO.setClave(Util.getValue(data, Numero.TRECE.valueI));
				usuarioDTO.setIdEstado(Util.getValue(data, Numero.CATORCE.valueI));

				configUsuarioDTO.setPersonasDTO(personaDTO);
				configUsuarioDTO.setUsuariosDTO(usuarioDTO);
				List<EmpresasDTO> empresasRolesUsuario = consultarEmpresasRoles(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
				// Se consultan los roles empresa usuario asignados al usuario encontrado en la
				// busqueda
				if (empresasRolesUsuario != null && !empresasRolesUsuario.isEmpty()) {
					configUsuarioDTO.setListEmpresasDTO(empresasRolesUsuario);
				} 
				response.agregarRegistro(configUsuarioDTO);
			}
			
		}
		
		return response;
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
		HashMap<Long, UsuariosRolesEmpresasDTO> mapUsuEmpresas = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			for (Object[] data : result) {
				UsuariosRolesEmpresasDTO usuRolesEmpresasDTO = new UsuariosRolesEmpresasDTO();
				usuRolesEmpresasDTO.setIdRol(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
				usuRolesEmpresasDTO.setIdEmpresa(Long.valueOf(Util.getValue(data, Numero.UNO.valueI)));
				usuRolesEmpresasDTO.setIdUsuario(Long.valueOf(Util.getValue(data, Numero.DOS.valueI)));
				usuRolesEmpresasDTO.setIdEstado(Util.getValue(data, Numero.TRES.valueI));

				mapUsuEmpresas.put(usuRolesEmpresasDTO.getIdEmpresa(), usuRolesEmpresasDTO);
			}

		}
		Collection<UsuariosRolesEmpresasDTO> empresasUsuario = mapUsuEmpresas.values();
		listUsuRolesEmpresasDTO.addAll(empresasUsuario);
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
	public ConfiguracionUsuarioDTO crearEditarUsuario(ConfiguracionUsuarioDTO configuracionUsuarioDTO)
			throws BusinessException {
		try {
			Builder<UsuariosDTO, Usuarios> builderUsuario = new Builder<>(Usuarios.class);
			Builder<PersonasDTO, Personas> builderPersona = new Builder<>(Personas.class);
			Personas personaSave = builderPersona.copy(configuracionUsuarioDTO.getPersonasDTO());
			Usuarios usuariosaSave = builderUsuario.copy(configuracionUsuarioDTO.getUsuariosDTO());
			if (configuracionUsuarioDTO.getPersonasDTO().getIdPersona() == null) {
				personaRepository.save(personaSave);
				Long idUsuario = personaSave.getIdPersona();
				usuariosaSave.setIdUsuario(idUsuario);
				String claveInicialGenerica = crearContrGenerica(personaSave);
				configuracionUsuarioDTO.getUsuariosDTO().setClave(claveInicialGenerica);
				usuariosaSave.setClave(passwordEncoder.encode(claveInicialGenerica));
				usuariosaSave.setPrimerIngreso(BigDecimal.ONE.longValue());
				usuarioRepository.save(usuariosaSave);

				// Se crear relacion en tabla roles empresa y despues en usuarios roles
				// empresa
				for (EmpresasDTO empresaRoles : configuracionUsuarioDTO.getListEmpresasDTO()) {

					for (Long idRol : empresaRoles.getIdRoles()) {

						em.createNativeQuery(SQLConstant.INSERT_USUARIOS_ROLES_EMPRESAS)
								.setParameter("idUsuario", idUsuario).setParameter("idRol", idRol)
								.setParameter("idEmpresa", empresaRoles.getIdEmpresa())
								.setParameter("idEstado", EstadoEnum.ACTIVO.name()).executeUpdate();

					}
				}
			} else {
				usuariosaSave.setPrimerIngreso(BigDecimal.ZERO.longValue());
				personaRepository.save(personaSave);
				usuarioRepository.save(usuariosaSave);
			}

		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		return configuracionUsuarioDTO;

	}

	/**
	 * Este metodo permite armar la lista de empresas y roles a partir de la lista
	 * de usuarios roles empresa
	 * 
	 * @param listUsuRolesEmpresasDTO
	 * @return
	 * @throws BusinessException
	 */
	public ConfiguracionUsuarioDTO obtenerEmpresasYRoles(List<UsuariosRolesEmpresasDTO> listUsuRolesEmpresasDTO)
			throws BusinessException {
		// Se consultan los roles empresa usuario asignados al usuario encontrado en la
		// busqueda
		ConfiguracionUsuarioDTO configuracionUsuarioDTO = new ConfiguracionUsuarioDTO();
		try {
			// Se arman las listas de roles y empresas asociadas al usuario
			List<EmpresasDTO> empresasUsuarioDTO = new ArrayList<>();
			List<RolesDTO> rolesUsuarioDTO = new ArrayList<>();
			List<Long> idRoles = new ArrayList<>();
			for (UsuariosRolesEmpresasDTO usuaRolEmpresasDTO : listUsuRolesEmpresasDTO) {
				empresasUsuarioDTO.add(consultarEmpresaById(usuaRolEmpresasDTO.getIdEmpresa()));
				idRoles.add(usuaRolEmpresasDTO.getIdRol());

			}
			configuracionUsuarioDTO.setPersonasDTO(new PersonasDTO());
			configuracionUsuarioDTO.setUsuariosDTO(new UsuariosDTO());
			configuracionUsuarioDTO.setListEmpresasDTO(empresasUsuarioDTO);
			configuracionUsuarioDTO.setListRolesDTO(rolesUsuarioDTO);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

		return configuracionUsuarioDTO;

	}

	/**
	 * Metodo encargado de actualizar la contraseña para ingresar por primera vez al
	 * sistema
	 * 
	 * @param contraseña contraseña actualizada por el usuario
	 * @throws BusinessException
	 */
	public void actContrasPrimerVez(Long idUsuario, String contrasena) throws BusinessException {
		try {
			Optional<Usuarios> usuario = usuarioRepository.findById(idUsuario);
			usuario.get().setClave(passwordEncoder.encode(contrasena));
			usuario.get().setPrimerIngreso(BigDecimal.ZERO.longValue());
			usuarioRepository.save(usuario.get());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

	}

	/**
	 * Genera la clave inicial generica de un usuario concatenando primera letra del
	 * primer nombre primerapellido numero de documento
	 * 
	 * @param persona
	 * @return
	 */
	private String crearContrGenerica(Personas persona) {
		String contrasena = "";
		contrasena = contrasena.concat(persona.getPrimerNombre().substring(0, 1).toUpperCase()).concat(persona
				.getPrimerApellido().toUpperCase().concat(persona.getNumeroDocumento().substring(0, 2).toUpperCase()));

		return contrasena;

	}

	/**
	 * Metodo encargado de consultar las empresas con sus roles asignados
	 * 
	 * @param idUSuario
	 * @return List<EmpresasDTO> lista de relacion entre empresas y roles
	 * @throws BusinessException
	 */
	public List<EmpresasDTO> consultarEmpresasRoles(Long idUsuario) throws BusinessException {
		Query q = em.createNativeQuery(SQLConstant.SELECT_ROLES_EMPRESAS_ID_USU).setParameter("idUSuario", idUsuario)
				.setParameter("idEstado", EstadoEnum.ACTIVO.name());
		List<Object[]> result = q.getResultList();
		List<EmpresasDTO> listUsuEmpresasDTO = new ArrayList<>();
		List<EmpresasDTO> listEmpresaRolFinal = new ArrayList<>();
		if (result != null && !result.isEmpty()) {
			for (Object[] data : result) {
				EmpresasDTO empresaRoll = new EmpresasDTO();
				List<Long> idRol = new ArrayList<>();
				idRol.add(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
				empresaRoll.setIdRoles(idRol);
				empresaRoll.setIdEmpresa(Long.valueOf(Util.getValue(data, Numero.UNO.valueI)));
				empresaRoll.setRazonSocial(Util.getValue(data, Numero.DOS.valueI));
				listUsuEmpresasDTO.add(empresaRoll);
			}

			Collection<List<EmpresasDTO>> agruparRolesEmpr = listUsuEmpresasDTO.stream()
					.collect(Collectors.groupingBy(EmpresasDTO::getIdEmpresa)).values();
			for (List<EmpresasDTO> listUsuaEmp : agruparRolesEmpr) {
				List<Long> idRoles = new ArrayList<>();
				EmpresasDTO empresaRol = new EmpresasDTO();
				for (EmpresasDTO usuariosRolesEmpresasDTO : listUsuaEmp) {
					idRoles.add(usuariosRolesEmpresasDTO.getIdRoles().iterator().next());
				}
				empresaRol.setIdEmpresa(listUsuaEmp.iterator().next().getIdEmpresa());
				empresaRol.setIdRoles(idRoles);
				empresaRol.setRazonSocial(listUsuaEmp.iterator().next().getRazonSocial());
				listEmpresaRolFinal.add(empresaRol);
			}
		}
		return listEmpresaRolFinal;
	}

	/**
	 * Metodo que permite configurar los filtros de busqueda para obtener los
	 * usuarios existentes en el sistema
	 */
	private void setFiltrosBusquedaUsuarios(StringBuilder sql, ArrayList<Object> parametros, FiltroBusquedaDTO filtro) {

		// filtro por tipo doc usuario
		String tipoDoc = filtro.getTipoDocumento();
		if (!Util.isNull(tipoDoc)) {
			sql.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			sql.append("UPPER(P.ID_TIPO_DOCUMENTO)=UPPER(?)");
			parametros.add(tipoDoc);
		}

		// filtro por numero doc usuario
		String numeroDoc = filtro.getNumeroDocumento();
		if (!Util.isNull(numeroDoc)) {
			sql.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			sql.append("UPPER(P.NUMERO_DOCUMENTO)=UPPER(?)");
			parametros.add(numeroDoc);
		}
	}

}