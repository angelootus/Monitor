package com.monitor.model.dto;

public class SitioDTO {
	private String cveSitio;
	private String ubicacion;
	private Long  numFotos;
	private String cveCampana;
	private String cvePlaza;
	private String cveClipro;
	private Integer iluminacion;
	private Integer status;
	private CampanaDTO campana;
	private PlazaDTO plaza;
	private CliProDTO clipro;
	
	public String getCveSitio() {
		return cveSitio;
	}
	public void setCveSitio(String cveSitio) {
		this.cveSitio = cveSitio;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public Long getNumFotos() {
		return numFotos;
	}
	public void setNumFotos(Long numFotos) {
		this.numFotos = numFotos;
	}
	public String getCveCampana() {
		return cveCampana;
	}
	public void setCveCampana(String cveCampana) {
		this.cveCampana = cveCampana;
	}
	public String getCvePlaza() {
		return cvePlaza;
	}
	public void setCvePlaza(String cvePlaza) {
		this.cvePlaza = cvePlaza;
	}
	public String getCveClipro() {
		return cveClipro;
	}
	public void setCveClipro(String cveClipro) {
		this.cveClipro = cveClipro;
	}
	public Integer getIluminacion() {
		return iluminacion;
	}
	public void setIluminacion(Integer iluminacion) {
		this.iluminacion = iluminacion;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public CampanaDTO getCampana() {
		return campana;
	}
	public void setCampana(CampanaDTO campana) {
		this.campana = campana;
	}
	public PlazaDTO getPlaza() {
		return plaza;
	}
	public void setPlaza(PlazaDTO plaza) {
		this.plaza = plaza;
	}
	public CliProDTO getClipro() {
		return clipro;
	}
	public void setClipro(CliProDTO clipro) {
		this.clipro = clipro;
	}
}
