package com.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.filter.Filtros;
import com.monitor.filter.FiltrosCampana;
import com.monitor.filter.Paginacion;
import com.monitor.model.dto.CampanaDTO;
import com.monitor.persistencia.Persistencia;
import com.monitor.service.CampanaService;
import com.monitor.util.Navigation;
import com.monitor.util.Util;


@ManagedBean
@ViewScoped
public class CatalogoCampana implements Navigation {
	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogoCampana.class);
	
	@ManagedProperty("#{persistencia}")
	public Persistencia persistencia;
	
	@ManagedProperty("#{currentData}")
	public CurrentData currentData;

	private Paginacion paginacion;
	private FiltrosCampana filtrosCampana;
	private CampanaDTO campana;
	private List<CampanaDTO> campanasDTOList;
	private CampanaService campanaService;
	private HttpServletRequest request;

    @PostConstruct
	public void init() {
		try {
			filtrosCampana = new FiltrosCampana();
		    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			campanaService = new CampanaService(persistencia.getEntityManager());
			campanasDTOList = campanaService.consultarCampanas(filtrosCampana);
			paginacion = new Paginacion();
			paginacion.setModel(campanasDTOList);
			paginacion.setPageIndex(0);
			if (campanasDTOList.size() > 0)
				campana = campanasDTOList.get(paginacion.getPageIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Paginacion getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(Paginacion paginacion) {
		this.paginacion = paginacion;
	}

	public CampanaDTO getCampana() {
		return campana;
	}

	public void setCampana(CampanaDTO campana) {
		this.campana = campana;
	}

	public Persistencia getPersistencia() {
		return persistencia;
	}

	public void setPersistencia(Persistencia persistencia) {
		this.persistencia = persistencia;
	}

	public CurrentData getCurrentData() {
		return currentData;
	}

	public void setCurrentData(CurrentData currentData) {
		this.currentData = currentData;
	}

	public FiltrosCampana getFiltrosCampana() {
		return filtrosCampana;
	}

	public void setFiltrosCampana(FiltrosCampana filtrosCampana) {
		this.filtrosCampana = filtrosCampana;
	}

	public void next() {
		update(filtrosCampana);
		paginacion.next();
		campana = campanasDTOList.get(paginacion.getPageIndex());
	}
	
	public void prev() {
		update(filtrosCampana);
		paginacion.prev();
		campana = campanasDTOList.get(paginacion.getPageIndex());
	}

	public void irA() {
	    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String irA = request.getParameter("formCatalogo:irA");
        if (Util.isParsable(irA)) {
        	int index = Integer.parseInt(irA)-1;
        	if (index >= 0 && index < paginacion.getRecordsTotal()){
        		paginacion.setPageIndex(index);
        		update(filtrosCampana);
        	} else {
        		paginacion.setIrA(0);
        	}
        }
	}
	
	public void busqueda() {
		filtrosCampana = new FiltrosCampana();
	    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String txtCliente = request.getParameter("formCatalogo:txtClienteHidden");
		String txtClienteNombre = request.getParameter("formCatalogo:txtClienteNombreHidden");
		String txtCveCampana = request.getParameter("formCatalogo:txtCveCampanaHidden");
		String txtNombre = request.getParameter("formCatalogo:txtNombre");
		LOGGER.debug("txtCliente: " + txtCliente);
		LOGGER.debug("txtClienteNombre: " + txtClienteNombre);
		LOGGER.debug("txtCveCampana: " + txtCveCampana);
		LOGGER.debug("txtNombre: " + txtNombre);
		filtrosCampana.setCveClipro(txtCliente);
		filtrosCampana.setClipro(txtClienteNombre);
		filtrosCampana.setCveCampana(txtCveCampana);
		filtrosCampana.setNombre(txtNombre);
//		paginacion.setPageIndex(0);
		update(filtrosCampana);
	}
	
	public void update(Filtros filtrosCampana) {
		try {
//			campana = null;
			campana = new CampanaDTO();
			campanasDTOList = campanaService.consultarCampanas((FiltrosCampana) filtrosCampana);
			paginacion.setModel(campanasDTOList);
			if (campanasDTOList.size() > 0)
				campana = campanasDTOList.get(paginacion.getPageIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminar() {
		try {
			filtrosCampana = new FiltrosCampana();
		    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String txtCveCampana = request.getParameter("formCatalogo:txtCveCampanaHidden");
			LOGGER.debug("txtCveCampana: " + txtCveCampana);
			filtrosCampana.setCveCampana(txtCveCampana);
			campanaService.eliminaCampana(filtrosCampana);
			filtrosCampana = new FiltrosCampana();
			update(filtrosCampana);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizar() {
		try {
			filtrosCampana = new FiltrosCampana();
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	        String txtCliente = request.getParameter("formCatalogo:txtClienteHidden");
	        String txtClienteNombre = request.getParameter("formCatalogo:txtClienteNombreHidden");
			String txtCveCampana = request.getParameter("formCatalogo:txtCveCampanaHidden");
			String txtNombre = request.getParameter("formCatalogo:txtNombre");
//			String txtFechaAlta = request.getParameter("formCatalogo:txtFechaAltaHidden");
			String txtInicia = request.getParameter("formCatalogo:txtInicia_input");
			String txtTermina = request.getParameter("formCatalogo:txtTermina_input");
			String statusCampana = request.getParameter("formCatalogo:statusCampana");

			filtrosCampana.setCveCampana(txtCveCampana);
			filtrosCampana.setNombre(txtNombre);
//			if (txtFechaAlta != null){
//				filtrosCampana.setFechaAlta(formatter2.parse(txtFechaAlta));
//			}
			if (txtInicia != null){
				filtrosCampana.setInicia(formatter.parse(txtInicia));
			}
			if (txtTermina != null){
				filtrosCampana.setTermina(formatter.parse(txtTermina));
			}
	        if (Util.isParsable(statusCampana)) {
				filtrosCampana.setStatus(Integer.parseInt(statusCampana));
	        }
			
			campanaService.actualizaCampana(filtrosCampana);
			filtrosCampana = new FiltrosCampana();
			update(filtrosCampana);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
