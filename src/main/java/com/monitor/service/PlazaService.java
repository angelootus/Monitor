package com.monitor.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import com.monitor.dao.PlazaDao;
import com.monitor.model.Plaza;
import com.monitor.model.dto.PlazaDTO;
import com.monitor.util.Util;

public class PlazaService {
	private EntityManager entityManager;
	private PlazaDao plazaDao;
	private Util util;

	public PlazaService(EntityManager entityManager) {
		this.entityManager = entityManager;
		plazaDao = new PlazaDao(entityManager);
		util = new Util();
	}

	public ArrayList<PlazaDTO> consultaPlazasActivas(String cveClipro) {
		ArrayList<Object[]> plazaList =(ArrayList<Object[]>)plazaDao.consultaPlazasActivas(cveClipro);
		ArrayList<PlazaDTO> plazaDTOList = util.getPlazasDTO(plazaList);
		return plazaDTOList;
	}
	
	public ArrayList<PlazaDTO> consultaPlazas() {
		ArrayList<Object[]> plazaList =(ArrayList<Object[]>)plazaDao.consultaPlazas();
		ArrayList<PlazaDTO> plazaDTOList = util.getPlazaDTO(plazaList);
		return plazaDTOList;
	}
}
