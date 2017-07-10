package com.monitor.model.dto;

import java.util.Date;

public class UsuarioDTO {
	private String email;
	private String nombre;
	private String apellidos;
	private String contrasena;
	private Integer tipo;
	private Date fechaalta;
	private Integer status ;
	private CliProDTO clipro;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public Date getFechaalta() {
		return fechaalta;
	}
	public void setFechaalta(Date fechaalta) {
		this.fechaalta = fechaalta;
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
}
