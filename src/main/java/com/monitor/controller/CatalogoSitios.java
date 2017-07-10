package com.monitor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.filter.Filtros;
import com.monitor.filter.FiltrosSitios;
import com.monitor.filter.FiltrosUsuario;
import com.monitor.filter.Paginacion;
import com.monitor.model.dto.CampanaDTO;
import com.monitor.model.dto.PlazaDTO;
import com.monitor.model.dto.SitioDTO;
import com.monitor.persistencia.Persistencia;
import com.monitor.service.CampanaService;
import com.monitor.service.PlazaService;
import com.monitor.service.SitioService;
import com.monitor.util.Navigation;
import com.monitor.util.Util;

@ManagedBean
@ViewScoped
public class CatalogoSitios implements Navigation, Serializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogoSitios.class);

	@ManagedProperty("#{persistencia}")
	public Persistencia persistencia;

	@ManagedProperty("#{currentData}")
	public CurrentData currentData;

	private FiltrosSitios filtrosSitios;
	private Paginacion paginacion;
	private SitioDTO sitio;
	private List<SitioDTO> sitiosDTOList;
	private List<SitioDTO> sitiosTreeDTOList;
	private List<PlazaDTO> plazaDTOList;
	private List<CampanaDTO> campanaDTOList;
	private SitioService sitioService;
	private HttpServletRequest request;
	private Integer orden;
	private TreeNode root;
	private CampanaService campanaService;
	private PlazaService plazaService;

	@PostConstruct
	public void init() {
		try {
			filtrosSitios = new FiltrosSitios();
			request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			sitioService = new SitioService(persistencia.getEntityManager());
			campanaService = new CampanaService(persistencia.getEntityManager());
			plazaService = new PlazaService(persistencia.getEntityManager());

			sitiosDTOList = sitioService.consultarSitios(filtrosSitios);
			plazaDTOList = plazaService.consultaPlazas();
			campanaDTOList = campanaService.consultaCampanas();

			paginacion = new Paginacion();
			paginacion.setModel(sitiosDTOList);
			paginacion.setPageIndex(0);
			if (sitiosDTOList.size() > 0)
				sitio = sitiosDTOList.get(paginacion.getPageIndex());
			busquedaTree();
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

	public SitioDTO getSitio() {
		return sitio;
	}

	public void setSitioDTO(SitioDTO sitio) {
		this.sitio = sitio;
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

	public FiltrosSitios getFiltrosSitios() {
		return filtrosSitios;
	}

	public void setFiltroSitios(FiltrosSitios filtrosSitios) {
		this.filtrosSitios = filtrosSitios;
	}

	public void next() {
		update(filtrosSitios);
		paginacion.next();
		sitio = sitiosDTOList.get(paginacion.getPageIndex());
	}

	public void prev() {
		update(filtrosSitios);
		paginacion.prev();
		sitio = sitiosDTOList.get(paginacion.getPageIndex());
	}

	@Override
	public void irA() {
	    request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String irA = request.getParameter("formCatalogo:irA");
        if (Util.isParsable(irA)) {
        	int index = Integer.parseInt(irA)-1;
        	if (index >= 0 && index < paginacion.getRecordsTotal()){
        		paginacion.setPageIndex(index);
    			update(filtrosSitios);
        	} else {
        		paginacion.setIrA(0);
        	}
        }
	}

	public void busqueda() {
		filtrosSitios = new FiltrosSitios();
		request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String txtCliente = request.getParameter("formCatalogo:txtClienteHidden");
		String txtClienteNombre = request.getParameter("formCatalogo:txtClienteNombreHidden");
		String txtCveSitio = request.getParameter("formCatalogo:txtCveSitioHidden");
		String cboCampana = request.getParameter("formCatalogo:cboCampana_input");
		String cboPlaza = request.getParameter("formCatalogo:cboPlaza_input");
		LOGGER.debug("txtCliente: " + txtCliente);
		LOGGER.debug("txtClienteNombre: " + txtClienteNombre);
		LOGGER.debug("txtCveSitio: " + txtCveSitio);
		LOGGER.debug("cboCampana: " + cboCampana);
		LOGGER.debug("cboPlaza: " + cboPlaza);
		filtrosSitios.setCveClipro(txtCliente);
		filtrosSitios.setClipro(txtClienteNombre);
		filtrosSitios.setCveSitio(txtCveSitio);
		filtrosSitios.setCveCampana(cboCampana);
		filtrosSitios.setCvePlaza(cboPlaza);
//		paginacion.setPageIndex(0);
		update(filtrosSitios);
	}

	public void busquedaTree() {
		filtrosSitios = new FiltrosSitios();
		request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		// String txtCliente = request.getParameter("formCatalogo:txtCliente");
		String txtCliente = currentData.getUsuario().getCliPro().getCveClipro();
		String rbnOrdenar = request.getParameter("formCatalogo:rbnOrdenar");
		LOGGER.debug("txtCliente: " + txtCliente);
		LOGGER.debug("rbnOrdenar: " + rbnOrdenar);

		// filtrosSitios.setCveClipro(txtCliente);
		if (Util.isParsable(rbnOrdenar)) {
			filtrosSitios.setOrden(Integer.parseInt(rbnOrdenar));
		} else {
			filtrosSitios.setOrden(1);
		}
		orden = filtrosSitios.getOrden();
		updateTree(filtrosSitios);
	}

	public void update(Filtros filtrosSitios) {
		try {
//			sitio = null;
			sitio = new SitioDTO();
			sitiosDTOList = sitioService.consultarSitios((FiltrosSitios) filtrosSitios);
			paginacion.setModel(sitiosDTOList);
			if (sitiosDTOList.size() > 0)
				sitio = sitiosDTOList.get(paginacion.getPageIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTree(Filtros filtrosSitios) {
		try {
			sitiosTreeDTOList = sitioService.consultarTreeSitios((FiltrosSitios) filtrosSitios);

			if ( ((FiltrosSitios) filtrosSitios).getOrden() != null && ( ((FiltrosSitios) filtrosSitios).getOrden() >= 0) && ((FiltrosSitios) filtrosSitios).getOrden() <= 2 )  {
				Integer order = ((FiltrosSitios) filtrosSitios).getOrden();
//				order=2;
				LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> tree = formaArbol(order);
				pintaArbol(order, tree);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> formaArbol(Integer order) {
		LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> tree = new LinkedHashMap<>();
		LinkedHashMap<String, ArrayList<String>> subtree = new LinkedHashMap<>();
		ArrayList<String> leafs = new ArrayList<>();
		for (SitioDTO sitioDTO : sitiosDTOList) {
			String currentPlaza = sitioDTO.getPlaza().getCvePlaza();
			String currentCampana = sitioDTO.getCampana().getCveCampana();
			switch (order) {
			case 1:
				for (SitioDTO sitioDTO2 : sitiosDTOList) {
					String plaza = sitioDTO2.getPlaza().getCvePlaza();
					if (currentPlaza.equals(plaza)){
						subtree.put(sitioDTO2.getCampana().getNombre(), leafs);
						tree.put(sitioDTO.getPlaza().getNombre(), subtree);
						currentCampana = sitioDTO2.getCampana().getCveCampana();
						for (SitioDTO sitioDTO3 : sitiosDTOList) {
							String campana = sitioDTO3.getCampana().getCveCampana();
							String newPlaza = sitioDTO3.getPlaza().getCvePlaza();
							if (currentPlaza.equals(newPlaza) && currentCampana.equals(campana)){
								leafs.add(sitioDTO3.getCveSitio());
							}
						}
						leafs = new ArrayList<>();
					}
				}
				leafs = new ArrayList<>();
				subtree = new LinkedHashMap<>();
				
				break;
			case 2:
				for (SitioDTO sitioDTO2 : sitiosDTOList) {
					String campana = sitioDTO2.getCampana().getCveCampana();
					if (currentCampana.equals(campana)){
						subtree.put(sitioDTO2.getPlaza().getNombre(), leafs);
						tree.put(sitioDTO.getCampana().getNombre(), subtree);
						currentPlaza = sitioDTO2.getPlaza().getCvePlaza();
						for (SitioDTO sitioDTO3 : sitiosDTOList) {
							String plaza = sitioDTO3.getPlaza().getCvePlaza();
							String newCampana = sitioDTO3.getCampana().getCveCampana();
							if (currentCampana.equals(newCampana) && currentPlaza.equals(plaza)){
								leafs.add(sitioDTO3.getCveSitio());
							}
						}
						leafs = new ArrayList<>();
					}
				}
				leafs = new ArrayList<>();
				subtree = new LinkedHashMap<>();
				
				break;
			default:
				for (SitioDTO sitioDTO2 : sitiosDTOList) {
					String plaza = sitioDTO2.getPlaza().getCvePlaza();
					if (currentPlaza.equals(plaza)){
						subtree.put(sitioDTO2.getCampana().getNombre(), leafs);
						tree.put(sitioDTO.getPlaza().getNombre(), subtree);
						currentCampana = sitioDTO2.getCampana().getCveCampana();
						for (SitioDTO sitioDTO3 : sitiosDTOList) {
							String campana = sitioDTO3.getCampana().getCveCampana();
							String newPlaza = sitioDTO3.getPlaza().getCvePlaza();
							if (currentPlaza.equals(newPlaza) && currentCampana.equals(campana)){
								leafs.add(sitioDTO3.getCveSitio());
							}
						}
						leafs = new ArrayList<>();
					}
				}
				leafs = new ArrayList<>();
				subtree = new LinkedHashMap<>();
				break;
			}
		}
		return tree;
	}

	private void pintaArbol(Integer order, LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> tree) {
		TreeNode node0 = null;
		TreeNode node00 = null;
		TreeNode node000 = null;
		TreeNode node0000 = null;
		switch (order) {
		case 1:
			root = new DefaultTreeNode("Plaza", null);
			node0 = new DefaultTreeNode("Plaza", root);
			break;
		case 2:
			root = new DefaultTreeNode("Campaña", null);
			node0 = new DefaultTreeNode("Campaña", root);
			break;
		default:
			root = new DefaultTreeNode("Plaza", null);
			node0 = new DefaultTreeNode("Plaza", root);
			break;
		}
		for (Entry<String, LinkedHashMap<String, ArrayList<String>>> item : tree.entrySet()) {
//					System.out.println(item.getKey() + "/" + item.getValue());
			node00 = new DefaultTreeNode(item.getKey(), node0);

			for (Entry<String, ArrayList<String>> subitem : item.getValue().entrySet()) {
//						System.out.println(subitem.getKey() + "/" + subitem.getValue());
				node000 = new DefaultTreeNode(subitem.getKey(), node00);
				
				for (String lastitem : subitem.getValue()) {
//							System.out.println("/" + lastitem);
					node000.getChildren().add(new DefaultTreeNode(lastitem));
//							node0000 = new DefaultTreeNode(lastitem, node000);
				}
			}
		}
	}

	@Override
	public void eliminar() {
		try {
			filtrosSitios = new FiltrosSitios();
			request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String txtCveSitio = request.getParameter("formCatalogo:txtCveSitioHidden");
			LOGGER.debug("txtCveSitio: " + txtCveSitio);

			filtrosSitios.setCveSitio(txtCveSitio);
			sitioService.eliminaSitio(filtrosSitios);
//			paginacion.setPageIndex(0);
			filtrosSitios = new FiltrosSitios();
			update(filtrosSitios);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void actualizar() {
		try {
			filtrosSitios = new FiltrosSitios();
			request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String txtCliente = request.getParameter("formCatalogo:txtClienteHidden");
			String txtClienteNombre = request.getParameter("formCatalogo:txtClienteNombreHidden");
			String txtCveSitio = request.getParameter("formCatalogo:txtCveSitioHidden");
			String cboCampana = request.getParameter("formCatalogo:cboCampana_input");
			String cboPlaza = request.getParameter("formCatalogo:cboPlaza_input");
			String txtUbicacion = request.getParameter("formCatalogo:txtUbicacion");
			String rbnIluminacion = request.getParameter("formCatalogo:rbnIluminacion");
			String rbnEstatus = request.getParameter("formCatalogo:rbnEstatus");

			filtrosSitios.setCveClipro(txtCliente);
			filtrosSitios.setClipro(txtClienteNombre);
			filtrosSitios.setCveSitio(txtCveSitio);
			filtrosSitios.setCveCampana(cboCampana);
			filtrosSitios.setCvePlaza(cboPlaza);
			filtrosSitios.setUbicacion(txtUbicacion);
			if (Util.isParsable(rbnIluminacion)) {
				filtrosSitios.setIluminacion(Integer.parseInt(rbnIluminacion));
			}
			if (Util.isParsable(rbnEstatus)) {
				filtrosSitios.setStatus(Integer.parseInt(rbnEstatus));
			}

			sitioService.actualizaSitio(filtrosSitios);
			filtrosSitios = new FiltrosSitios();
			update(filtrosSitios);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<PlazaDTO> getPlazaDTOList() {
		return plazaDTOList;
	}

	public void setPlazaDTOList(List<PlazaDTO> plazaDTOList) {
		this.plazaDTOList = plazaDTOList;
	}

	public List<CampanaDTO> getCampanaDTOList() {
		return campanaDTOList;
	}

	public void setCampanaDTOList(List<CampanaDTO> campanaDTOList) {
		this.campanaDTOList = campanaDTOList;
	}

}
