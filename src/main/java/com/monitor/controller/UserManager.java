package com.monitor.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.filter.FiltrosUsuario;
import com.monitor.model.Usuario;
import com.monitor.model.dto.UsuarioDTO;
import com.monitor.persistencia.Persistencia;
import com.monitor.service.UsuarioService;

@ManagedBean
@SessionScoped
public class UserManager {

	@ManagedProperty("#{persistencia}")
	public Persistencia persistencia;

	@ManagedProperty("#{currentData}")
	public CurrentData currentData;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

	public static final String HOME_PAGE_REDIRECT = "/secured/home.xhtml?faces-redirect=true";
	public static final String LOGOUT_PAGE_REDIRECT = "/logout.xhtml?faces-redirect=true";

	private String email;

	private String userPassword;
	private Usuario currentUser;

	public String login() {
		// lookup the user based on user name and user password
		currentUser = busca(email, userPassword);

		if (currentUser != null) {
			LOGGER.info("login successful for '{}'", email);

			return HOME_PAGE_REDIRECT;
		} else {
			LOGGER.info("login failed for '{}'", email);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario y/o Password Incorresto", ""));
			return "";
		}
	}

	public String logout() {

		String identifier = email;

		// invalidate the session
		LOGGER.debug("invalidating session for '{}'", identifier);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		LOGGER.info("logout successful for '{}'", identifier);
		return LOGOUT_PAGE_REDIRECT;
	}

	public String toHome() {
		LOGGER.info("Se envia al home");
		return HOME_PAGE_REDIRECT;

	}

	public boolean isLoggedIn() {
		return currentUser != null;
	}

	public String isLoggedInForwardHome() {
		if (isLoggedIn()) {
			return HOME_PAGE_REDIRECT;
		}

		return null;
	}

	private Usuario busca(String email, String password) {
		UsuarioService usuarioService = new UsuarioService(persistencia.getEntityManager());

		ArrayList<Usuario> arrayListUsuario = usuarioService.consultaLoginPorEmail(email);

		if (arrayListUsuario != null && arrayListUsuario.size() == 1) {
			Usuario usuario = arrayListUsuario.get(0);
			if (usuario != null && usuario.getEmail().equalsIgnoreCase(email)
					&& usuario.getContrasena().equals(password)) {
				currentData.setUsuario(usuario);

				return usuario;
			}
		}

		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Usuario getCurrentUser() {
		return currentUser;
	}

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

	// do not provide a setter for currentUser!
}
