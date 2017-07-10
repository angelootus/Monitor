package com.monitor.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.filter.FiltrosMonitor;
import com.monitor.filter.PaginacionMonitor;
import com.monitor.model.Foto;
import com.monitor.model.Usuario;
import com.monitor.model.dto.CampanaDTO;
import com.monitor.model.dto.FotoDTO;
import com.monitor.model.dto.PlazaDTO;
import com.monitor.model.dto.SitioDTO;
import com.monitor.persistencia.Persistencia;
import com.monitor.service.CampanaService;
import com.monitor.service.FotoService;
import com.monitor.service.PlazaService;
import com.monitor.service.SitioService;

@ManagedBean
@ViewScoped
public class MonitorControlerBean implements Serializable {
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorControlerBean.class);
	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{persistencia}")
	public Persistencia persistencia;
	@ManagedProperty("#{userManager.currentUser}")
	public Usuario currentUser;
	@ManagedProperty("#{paginacionSessionBean.filtrosMonitor}")
	private FiltrosMonitor filtrosMonitor;
	@ManagedProperty("#{paginacionSessionBean.view}")
	private String view;

	private PaginacionMonitor paginacionMonitor;
	private ArrayList<PlazaDTO> plazaDTOList;
	private ArrayList<CampanaDTO> campanaDTOList;
	private ArrayList<SitioDTO> sitioDTOList;

	private FotoService fotoService;
	private CampanaService campanaService;
	private PlazaService plazaService;
	private SitioService sitioService;
	private ArrayList<FotoDTO> fotolistMostrar;
	

	@PostConstruct
	public void init() {
		filtrosMonitor = new FiltrosMonitor();
		fotoService = new FotoService(persistencia.getEntityManager());
		campanaService = new CampanaService(persistencia.getEntityManager());
		plazaService = new PlazaService(persistencia.getEntityManager());
		sitioService = new SitioService(persistencia.getEntityManager());
		String cveCliPro =currentUser.getCliPro().getCveClipro();
		filtrosMonitor.setCveClipro(cveCliPro);
		plazaDTOList = plazaService.consultaPlazasActivas(currentUser.getCliPro().getCveClipro());
		campanaDTOList = campanaService.consultaCampanasActivas(currentUser.getCliPro().getCveClipro());
		sitioDTOList = sitioService.consultaSitiosActivos(currentUser.getCliPro().getCveClipro());
		getFotosToShow();

	}

	public PaginacionMonitor getPaginacionMonitor() {
		return paginacionMonitor;
	}

	public void setPaginacionMonitor(PaginacionMonitor paginacionMonitor) {
		this.paginacionMonitor = paginacionMonitor;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		System.out.println("view" + view);
		this.view = view;
	}

	public void irPagina() {
		if (paginacionMonitor.getPageIndex() < 1 || paginacionMonitor.getPageIndex() > paginacionMonitor.getPages()) {
			FacesMessage infoMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Página no encontrada",
					"La página introducida no es correcta.");
			FacesContext.getCurrentInstance().addMessage("messages", infoMsg);

		} else {
			paginacionMonitor.updateIrPagina();
		}

	}

	public void getFotosToShow() {
		try {
			System.out.println("se consultan las fotos");
			fotolistMostrar = (ArrayList<FotoDTO>) fotoService.obtenerFotosPorUsuario(filtrosMonitor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error: " , e);
		}
		paginacionMonitor = new PaginacionMonitor();
		paginacionMonitor.setModel(fotolistMostrar);

	}

	public FiltrosMonitor getFiltrosMonitor() {
		return filtrosMonitor;
	}

	public void setFiltrosMonitor(FiltrosMonitor filtrosMonitor) {
		this.filtrosMonitor = filtrosMonitor;

	}

	public Persistencia getPersistencia() {
		return persistencia;
	}

	public void setPersistencia(Persistencia persistencia) {
		this.persistencia = persistencia;
	}

	public ArrayList<PlazaDTO> getPlazaDTOList() {
		return plazaDTOList;
	}

	public void setPlazaDTOList(ArrayList<PlazaDTO> plazaDTOList) {
		this.plazaDTOList = plazaDTOList;
	}

	public ArrayList<CampanaDTO> getCampanaDTOList() {
		return campanaDTOList;
	}

	public void setCampanaDTOList(ArrayList<CampanaDTO> campanaDTOList) {
		this.campanaDTOList = campanaDTOList;
	}

	public ArrayList<SitioDTO> getSitioDTOList() {
		return sitioDTOList;
	}

	public void setSitioDTOList(ArrayList<SitioDTO> sitioDTOList) {
		this.sitioDTOList = sitioDTOList;
	}

	public Usuario getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Usuario currentUser) {
		this.currentUser = currentUser;
	}

	public void removeFilter() {
		filtrosMonitor.setFechaFin(null);
		filtrosMonitor.setFechaInicio(null);
		filtrosMonitor.setFilterCampana(null);
		filtrosMonitor.setFilterPlaza(null);
		filtrosMonitor.setFilterSitio(null);
		filtrosMonitor.setOrdenarPor("");
		getFotosToShow();

	}

	public ArrayList<FotoDTO> getFotolistMostrar() {
		return fotolistMostrar;
	}

	public void setFotolistMostrar(ArrayList<FotoDTO> fotolistMostrar) {
		this.fotolistMostrar = fotolistMostrar;
	}

	public String getIluminacionStr(int iluminacion) {
		String descripcion="";
		switch (iluminacion) {
		case 1:
			descripcion ="Si";
			break;
		case 2:
			descripcion ="No";
			break;

		}
		return descripcion;

	}





}