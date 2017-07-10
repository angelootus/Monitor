package com.monitor.model.dto;
// Generated 23/06/2017 12:40:03 PM by Hibernate Tools 5.2.3.Final

public class PlazaDTO {

	private String cvePlaza;
	private String nombre;
	private Integer status;
	private String padre;
	private Integer tipo;
	private String cveClipro;
	private Long  numFotos;
	
	public String getCvePlaza() {
		return cvePlaza;
	}
	public void setCvePlaza(String cvePlaza) {
		this.cvePlaza = cvePlaza;
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
	public String getPadre() {
		return padre;
	}
	public void setPadre(String padre) {
		this.padre = padre;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getCveClipro() {
		return cveClipro;
	}
	public void setCveClipro(String cveClipro) {
		this.cveClipro = cveClipro;
	}




}
