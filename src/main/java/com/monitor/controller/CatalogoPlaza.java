package com.monitor.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.monitor.filter.Filtros;
import com.monitor.persistencia.Persistencia;
import com.monitor.util.Navigation;


@ManagedBean
@SessionScoped
public class CatalogoPlaza implements Navigation {
	
	private Integer idUsuario;
	
	private String email;
	
	private String clavePlaza;
	
	private String plaza;
	
	@ManagedProperty("#{persistencia}")
	public Persistencia persistencia;
	
	
	@ManagedProperty("#{currentData}")
	public CurrentData currentData;

	public void init()
	{
		
	}
	

	public Integer getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	public String getClavePlaza() {
		return clavePlaza;
	}


	public void setClavePlaza(String clavePlaza) {
		this.clavePlaza = clavePlaza;
	}


	public String getPlaza() {
		return plaza;
	}


	public void setPlaza(String plaza) {
		this.plaza = plaza;
	}


	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void prev() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void irA() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void busqueda() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(Filtros filtrosPlaza) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		
	}
	
	

}
