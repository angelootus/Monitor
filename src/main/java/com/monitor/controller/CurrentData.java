package com.monitor.controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.monitor.model.Usuario;

@ManagedBean(name="currentData")
@ApplicationScoped
public class CurrentData {
	
	private Usuario usuario;
		
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
