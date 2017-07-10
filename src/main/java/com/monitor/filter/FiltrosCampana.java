package com.monitor.filter;

import java.util.Date;

import com.monitor.util.Util;

public class FiltrosCampana extends Filtros {

	private String cveCampana;
	private String nombre;
	private Date fechaAlta;
	private Date inicia;
	private Date termina;
	private Integer status;
	private String cveClipro;
	private String clipro;
	
	private Util util= new Util();

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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getFechaAlta() throws Exception {
		return util.formatDate(fechaAlta);
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getCveClipro() {
		return cveClipro;
	}
	public void setCveClipro(String cveClipro) {
		this.cveClipro = cveClipro;
	}
	public String getClipro() {
		return clipro;
	}
	public void setClipro(String clipro) {
		this.clipro = clipro;
	}
	public Util getUtil() {
		return util;
	}
	public void setUtil(Util util) {
		this.util = util;
	}
	public Date getInicia() throws Exception {
		return util.formatDate(inicia);
	}
	public void setInicia(Date inicia) {
		this.inicia = inicia;
	}
	public Date getTermina() throws Exception {
		return util.formatDate(termina);
	}
	public void setTermina(Date termina) {
		this.termina = termina;
	}
}
