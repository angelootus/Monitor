package com.monitor.model.dto;

public class CliProDTO {
	
	private String cveClipro;
	private String nombre;
	private Integer tipo;
	private String padre;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getPadre() {
		return padre;
	}
	public void setPadre(String padre) {
		this.padre = padre;
	}
	public String getCveClipro() {
		return cveClipro;
	}
	public void setCveClipro(String cveClipro) {
		this.cveClipro = cveClipro;
	}
}
