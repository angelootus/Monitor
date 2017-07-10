package com.monitor.model.dto;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.monitor.model.FotoId;
import com.monitor.model.Sitio;
import com.monitor.model.Usuario;

public class FotoDTO {
	private FotoId id;
	private Sitio sitio;
	private Usuario usuario;
	private String fotoPath;
	private String comentario;
	private Integer calificacion;
	private Double latitudGps;
	private Double longitudGps;
	
	public FotoId getId() {
		return id;
	}
	public void setId(FotoId id) {
		this.id = id;
	}
	public Sitio getSitio() {
		return sitio;
	}
	public void setSitio(Sitio sitio) {
		this.sitio = sitio;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getFotoPath() {
		return fotoPath;
	}
	public void setFotoPath(String fotoPath) {
		this.fotoPath = fotoPath;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Integer getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}
	public Double getLatitudGps() {
		return latitudGps;
	}
	public void setLatitudGps(Double latitudGps) {
		this.latitudGps = latitudGps;
	}
	public Double getLongitudGps() {
		return longitudGps;
	}
	public void setLongitudGps(Double longitudGps) {
		this.longitudGps = longitudGps;
	}



}
