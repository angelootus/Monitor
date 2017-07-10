package com.monitor.service;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import com.monitor.dao.FotoDao;
import com.monitor.filter.FiltrosMonitor;
import com.monitor.model.Foto;
import com.monitor.model.dto.FotoDTO;


public class FotoService {
	private EntityManager entityManager;
	private FotoDao fotoDao;
	
	public FotoService(EntityManager entityManager) {
		this.entityManager = entityManager;
		fotoDao = new FotoDao(entityManager);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ArrayList<FotoDTO> obtenerFotosPorUsuario(FiltrosMonitor filtrosMonitor) throws Exception {
		com.monitor.util.Util util = new com.monitor.util.Util();
		return (ArrayList<FotoDTO>)util.getFotoDTO((ArrayList<Foto>)fotoDao.obtenerFotosPorUsuario(filtrosMonitor));
	}

}
