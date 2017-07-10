package com.monitor.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.monitor.filter.FiltrosMonitor;

@ManagedBean
@SessionScoped
public class PaginacionSessionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FiltrosMonitor filtrosMonitor;
	private  String view = "listaSimple";
	@PostConstruct
	public void init() {
		filtrosMonitor = new FiltrosMonitor();
	}
	public FiltrosMonitor getFiltrosMonitor() {
		return filtrosMonitor;
	}
	public void setFiltrosMonitor(FiltrosMonitor filtrosMonitor) {
		this.filtrosMonitor = filtrosMonitor;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	

	
}
