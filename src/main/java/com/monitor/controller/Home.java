package com.monitor.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Home {
	
	
	@ManagedProperty("#{currentData}")
	public CurrentData currentData;
	
	
	private String mensaje; 
	
	public void init()
	{
		
		mensaje= currentData.getUsuario().getEmail();
		
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public CurrentData getCurrentData() {
		return currentData;
	}

	public void setCurrentData(CurrentData currentData) {
		this.currentData = currentData;
	}

}
