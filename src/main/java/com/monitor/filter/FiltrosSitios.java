package com.monitor.filter;

import com.monitor.util.Util;

public class FiltrosSitios extends Filtros {

	private String cveSitio;
	private String ubicacion;
	private Integer iluminacion;
	private Integer status;
	private String cveClipro;
	private String clipro;
	private String cveCampana;
	private String campana;
	private String cvePlaza;
	private String plaza;
	private Integer orden; 
	
	private Util util= new Util();

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
	public String getCveClipro() {
		return cveClipro;
	}
	public void setCveClipro(String cveClipro) {
		this.cveClipro = cveClipro;
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
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public Util getUtil() {
		return util;
	}
	public void setUtil(Util util) {
		this.util = util;
	}
	public String getClipro() {
		return clipro;
	}
	public void setClipro(String clipro) {
		this.clipro = clipro;
	}
	public String getCampana() {
		return campana;
	}
	public void setCampana(String campana) {
		this.campana = campana;
	}
	public String getPlaza() {
		return plaza;
	}
	public void setPlaza(String plaza) {
		this.plaza = plaza;
	}
}
