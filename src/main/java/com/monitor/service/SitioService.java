package com.monitor.service;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import com.monitor.dao.SitioDao;
import com.monitor.filter.FiltrosSitios;
import com.monitor.filter.FiltrosUsuario;
import com.monitor.model.dto.SitioDTO;
import com.monitor.model.dto.UsuarioDTO;
import com.monitor.util.Util;

public class SitioService {
	private EntityManager entityManager;
	private SitioDao sitioDao;
	private Util util;
	
	public SitioService(EntityManager entityManager) {
		this.entityManager = entityManager;
		sitioDao = new SitioDao(entityManager);
		util = new Util();
	}

	public ArrayList<SitioDTO> consultaSitiosActivos(String cveClipro) {
		ArrayList<Object[]> sitioList = (ArrayList<Object[]>)sitioDao.consultaSitiosActivos(cveClipro);
		ArrayList<SitioDTO> sitioDTOList = util.getSitioDTO(sitioList);		
		return sitioDTOList;
	}
	
	public <T> ArrayList<SitioDTO> consultarSitios(FiltrosSitios filtrosSitios) throws Exception {
		ArrayList<T[]> sitiosList = (ArrayList<T[]>)sitioDao.consultar(filtrosSitios);
		ArrayList<SitioDTO> sitioDTOList = util.getSitiosDTO(sitiosList);		
		return sitioDTOList;
	}
	
	public <T> ArrayList<SitioDTO> consultarTreeSitios(FiltrosSitios filtrosSitios) throws Exception {
		ArrayList<T[]> sitiosList = (ArrayList<T[]>)sitioDao.consultarTree(filtrosSitios);
		ArrayList<SitioDTO> sitioDTOList = util.getSitiosTreeDTO(sitiosList);		
		return sitioDTOList;
	}
	
	public void eliminaSitio(FiltrosSitios filtrosSitios) throws Exception {
		sitioDao.eliminar(filtrosSitios);
	}
	
	public void actualizaSitio(FiltrosSitios filtrosSitios) throws Exception {
		sitioDao.actualizar(filtrosSitios);
	}
}
