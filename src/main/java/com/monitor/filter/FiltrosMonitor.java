package com.monitor.filter;

import java.util.Date;

import com.monitor.util.Util;

public class FiltrosMonitor {

	private String ordenarPor = "";
	private String[] filterPlaza;
	private String[] filterCampana;
	private String[] filterSitio;
	private String cveClipro;
    private Date fechaInicio = null;
    private Date fechaFin = null;
    private Util util = new Util();
    
	public String getOrdenarPor() {
		System.out.println("getOrdenarPor " + ordenarPor);
		return ordenarPor;
	}

	public void setOrdenarPor(String ordenarPor) {
		System.out.println("setOrdenarPor " + ordenarPor);
		this.ordenarPor = ordenarPor;
	}

	public String[] getFilterPlaza() {
		return filterPlaza;
	}

	public void setFilterPlaza(String[] filterPlaza) {
		this.filterPlaza = filterPlaza;
	}

	public String[] getFilterCampana() {
		return filterCampana;
	}

	public void setFilterCampana(String[] filterCampana) {
		this.filterCampana = filterCampana;
	}

	public String[] getFilterSitio() {
		return filterSitio;
	}

	public void setFilterSitio(String[] filterSitio) {
		this.filterSitio = filterSitio;
	}



	public Date getFechaInicio() throws Exception {
		return util.formatDate(fechaInicio);
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() throws Exception {		
		return util.formatDate(fechaFin);
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCveClipro() {
		return cveClipro;
	}

	public void setCveClipro(String cveClipro) {
		this.cveClipro = cveClipro;
	}

}