package com.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.filter.Filtros;
import com.monitor.filter.FiltrosUsuario;
import com.monitor.filter.Paginacion;
import com.monitor.model.dto.UsuarioDTO;
import com.monitor.persistencia.Persistencia;
import com.monitor.service.UsuarioService;
import com.monitor.util.Navigation;
import com.monitor.util.Util;


@ManagedBean
@ViewScoped
public class CatalogoUsuarios implements Navigation {
	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogoUsuarios.class);
	
	@ManagedProperty("#{persistencia}")
	public Persistencia persistencia;
	
	@ManagedProperty("#{currentData}")
	public CurrentData currentData;

	private FiltrosUsuario filtrosUsuario;
	private Paginacion paginacion;
	private UsuarioDTO usuario;
	private List<UsuarioDTO> usuariosDTOList;
	private UsuarioService usuarioService;
	private HttpServletRequest request;

    @PostConstruct
	public void init() {
		try {
			filtrosUsuario = new FiltrosUsuario();
		    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			usuarioService = new UsuarioService(persistencia.getEntityManager());
			usuariosDTOList = usuarioService.consultarUsuarios(filtrosUsuario);
			paginacion = new Paginacion();
			paginacion.setModel(usuariosDTOList);
			paginacion.setPageIndex(0);
			if (usuariosDTOList.size() > 0)
				usuario = usuariosDTOList.get(paginacion.getPageIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Paginacion getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(Paginacion paginacion) {
		this.paginacion = paginacion;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

/*
	private List<Usuario> traerTodos() {
    	Usuario usuario = new Usuario();
    	usuario.consultaTodos();
    	
    	List<Usuario> usuariosList = new ArrayList<>();
    	usuariosList = (ArrayList<Usuario>) persistencia.busqueda(usuario);
        return usuarios;
    }
    
*/	
	public Persistencia getPersistencia() {
		return persistencia;
	}
	
	public void setPersistencia(Persistencia persistencia) {
		this.persistencia = persistencia;
	}

	public CurrentData getCurrentData() {
		return currentData;
	}

	public void setCurrentData(CurrentData currentData) {
		this.currentData = currentData;
	}

	public FiltrosUsuario getFiltrosUsuario() {
		return filtrosUsuario;
	}

	public void setFiltrosUsuario(FiltrosUsuario filtrosUsuario) {
		this.filtrosUsuario = filtrosUsuario;
	}

	public void next() {
		update(filtrosUsuario);
		paginacion.next();
		usuario = usuariosDTOList.get(paginacion.getPageIndex());
	}
	
	public void prev() {
		update(filtrosUsuario);
		paginacion.prev();
		usuario = usuariosDTOList.get(paginacion.getPageIndex());
	}

	public void irA() {
	    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String irA = request.getParameter("formCatalogo:irA");
        if (Util.isParsable(irA)) {
        	int index = Integer.parseInt(irA)-1;
        	if (index >= 0 && index < paginacion.getRecordsTotal()){
        		paginacion.setPageIndex(index);
            	update(filtrosUsuario);
        	} else {
        		paginacion.setIrA(0);
        	}
        }
	}
	
	public void busqueda() {
		filtrosUsuario = new FiltrosUsuario();
	    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String txtCliente = request.getParameter("formCatalogo:txtClienteHidden");
		String txtClienteNombre = request.getParameter("formCatalogo:txtClienteNombreHidden");
		String txtEmail = request.getParameter("formCatalogo:txtEmailHidden");
		String txtNombre = request.getParameter("formCatalogo:txtNombre");
		LOGGER.debug("txtCliente: " + txtCliente);
		LOGGER.debug("txtClienteNombre: " + txtClienteNombre);
		LOGGER.debug("txtEmail: " + txtEmail);
		LOGGER.debug("txtNombre: " + txtNombre);
		filtrosUsuario.setCveClipro(txtCliente);
		filtrosUsuario.setClipro(txtClienteNombre);
		filtrosUsuario.setNombre(txtNombre);
		filtrosUsuario.setEmail(txtEmail);
//		paginacion.setPageIndex(0);
		update(filtrosUsuario);
	}
	
	public void update(Filtros filtrosUsuario) {
		try {
//			usuario = null;
			usuario = new UsuarioDTO();
			usuariosDTOList = usuarioService.consultarUsuarios((FiltrosUsuario) filtrosUsuario);
			paginacion.setModel(usuariosDTOList);
			if (usuariosDTOList.size() > 0)
				usuario = usuariosDTOList.get(paginacion.getPageIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminar() {
		try {
			filtrosUsuario = new FiltrosUsuario();
		    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String txtEmail = request.getParameter("formCatalogo:txtEmailHidden");
			LOGGER.debug("txtEmail: " + txtEmail);
			filtrosUsuario.setEmail(txtEmail);
			usuarioService.eliminaUsuario(filtrosUsuario);
//			paginacion.setPageIndex(paginacion.getPageIndex()-1);
			filtrosUsuario = new FiltrosUsuario();
			update(filtrosUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizar() {
		try {
			filtrosUsuario = new FiltrosUsuario();
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
			
		    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	        String txtCliente = request.getParameter("formCatalogo:txtClienteHidden");
	        String txtClienteNombre = request.getParameter("formCatalogo:txtClienteNombreHidden");
//			String txtEmail = request.getParameter("formCatalogo:txtEmail");
			String txtEmail = request.getParameter("formCatalogo:txtEmailHidden");
			String txtContrasena = request.getParameter("formCatalogo:txtContrasena");
			String txtNombre = request.getParameter("formCatalogo:txtNombre");
			String txtApellidos = request.getParameter("formCatalogo:txtApellidos");
			String txtFechaAlta = request.getParameter("formCatalogo:txtFechaAlta_input");
			String tipoUsuario = request.getParameter("formCatalogo:tipoUsuario");
			String statusUsuario = request.getParameter("formCatalogo:statusUsuario");

//			filtrosUsuario.setCveClipro(txtCliente);
//			filtrosUsuario.setCveCliproNombre(txtClienteNombre);
			filtrosUsuario.setEmail(txtEmail);
			filtrosUsuario.setContrasena(txtContrasena);
			filtrosUsuario.setNombre(txtNombre);
			filtrosUsuario.setApellidos(txtApellidos);
			if (txtFechaAlta != null){
				filtrosUsuario.setFechaAlta(formatter.parse(txtFechaAlta));
			}
	        if (Util.isParsable(tipoUsuario)) {
	        	filtrosUsuario.setTipo(Integer.parseInt(tipoUsuario));
	        }
	        if (Util.isParsable(statusUsuario)) {
	        	filtrosUsuario.setStatus(Integer.parseInt(statusUsuario));
	        }
			
//			filtrosUsuario.setCveClipro(usuario.getClipro().getCveClipro());
//			filtrosUsuario.setCveCliproNombre(usuario.getClipro().getCveClipro());
//			filtrosUsuario.setEmail(usuario.getEmail());
//			filtrosUsuario.setContrasena(usuario.getContrasena());
//			filtrosUsuario.setNombre(usuario.getNombre());
//			filtrosUsuario.setApellidos(usuario.getApellidos());
//			filtrosUsuario.setFechaAlta(usuario.getFechaalta());
//			filtrosUsuario.setTipo(usuario.getTipo());
//			filtrosUsuario.setStatus(usuario.getStatus());
			usuarioService.actualizaUsuario(filtrosUsuario);
			filtrosUsuario = new FiltrosUsuario();
			update(filtrosUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
