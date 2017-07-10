package com.monitor.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.monitor.model.Foto;

@ManagedBean
@ViewScoped
public class LoadMapControllerBean {

	private Foto foto;

	public Foto getFoto() {
		System.out.println("foto get"  );
		return foto;
	}

	public void setFoto(Foto foto) {
		System.out.println("foto set"  );
		this.foto = foto;
	}


}
