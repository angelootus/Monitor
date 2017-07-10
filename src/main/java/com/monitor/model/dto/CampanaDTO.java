package com.monitor.model.dto;

import java.util.Date;

public class CampanaDTO {
	private String cveCampana;
	private String nombre;
	private Date fechaalta;
	private Date inicia;
	private Date termina;
	private Integer status;
	private CliProDTO clipro;
	private Long  numFotos;
	
	public String getCveCampana() {
		return cveCampana;
	}
	public void setCveCampana(String cveCampana) {
		this.cveCampana = cveCampana;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getNumFotos() {
		return numFotos;
	}
	public void setNumFotos(Long numFotos) {
		this.numFotos = numFotos;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public CliProDTO getClipro() {
		return clipro;
	}
	public void setClipro(CliProDTO clipro) {
		this.clipro = clipro;
	}
	public Date getFechaalta() {
		return fechaalta;
	}
	public void setFechaalta(Date fechaalta) {
		this.fechaalta = fechaalta;
	}
	public Date getInicia() {
		return inicia;
	}
	public void setInicia(Date inicia) {
		this.inicia = inicia;
	}
	public Date getTermina() {
		return termina;
	}
	public void setTermina(Date termina) {
		this.termina = termina;
	}
}
