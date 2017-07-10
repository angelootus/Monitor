package com.monitor.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.filter.Filtros;
import com.monitor.filter.FiltrosCampana;
import com.monitor.filter.FiltrosUsuario;
import com.monitor.model.CliPro;

public class CampanaDao implements MonitorDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(CampanaDao.class);
	private EntityManager entityManager;
	public CampanaDao(EntityManager entityManager) {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List consultaCampanasActivas(String cveClipro) {
		Query q = entityManager.createQuery(
				"SELECT count(f.id.cvePlaza), c.id.cveCampana,c.nombre FROM Campana as c LEFT JOIN Foto as f on (c.id.cveCampana = f.id.cveCampana) WHERE (c.status=1 and c.id.cveClipro=:cveClipro) group by f.id.cveCampana,c.id.cveCampana,c.nombre  order by c.nombre desc");
		q.setParameter("cveClipro", cveClipro);
		return q.getResultList();
	}

	public List consultaCampanas() {
//		Query q = entityManager.createQuery("select c from Campana as c where c.status=1 order by c.nombre desc");
		Query q = entityManager.createQuery("select c from Campana as c order by c.nombre desc");
		return q.getResultList();
	}

	@Override
	public void actualizar(Filtros filtrosCampana) throws Exception {
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		Query q = null;
		StringBuffer queryString = new StringBuffer("update Campana set");
		
		if (((FiltrosCampana) filtrosCampana).getNombre()!= null && ((FiltrosCampana) filtrosCampana).getNombre().length() > 0) {
			queryString.append(" nombre = :nombre,");
		}
		if (((FiltrosCampana) filtrosCampana).getFechaAlta()!= null) {
			queryString.append(" fechaalta = :fechaalta,");
		}
		if (((FiltrosCampana) filtrosCampana).getInicia()!= null) {
			queryString.append(" inicia = :inicia,");
		}
		if (((FiltrosCampana) filtrosCampana).getTermina()!= null) {
			queryString.append(" termina = :termina,");
		}
		if (((FiltrosCampana) filtrosCampana).getStatus()!= null && (((FiltrosCampana) filtrosCampana).getStatus() > 0 && ((FiltrosCampana) filtrosCampana).getStatus() < 3)) {
			queryString.append(" status = :status ");
		}
		queryString.append(" where cve_campana = :cve_campana");
		
		try {
			q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
			
			if (((FiltrosCampana) filtrosCampana).getNombre() != null && ((FiltrosCampana) filtrosCampana).getNombre().length() > 0) {
				q.setParameter("nombre", Arrays.asList(((FiltrosCampana) filtrosCampana).getNombre()));
			}
			if (((FiltrosCampana) filtrosCampana).getFechaAlta() != null) {
				q.setParameter("fechaalta", Arrays.asList(((FiltrosCampana) filtrosCampana).getFechaAlta()));
			}
			if (((FiltrosCampana) filtrosCampana).getInicia() != null) {
				q.setParameter("inicia", Arrays.asList(((FiltrosCampana) filtrosCampana).getInicia()));
			}
			if (((FiltrosCampana) filtrosCampana).getTermina() != null) {
				q.setParameter("termina", Arrays.asList(((FiltrosCampana) filtrosCampana).getTermina()));
			}
			if (((FiltrosCampana) filtrosCampana).getStatus()!= null && (((FiltrosCampana) filtrosCampana).getStatus() > 0 && ((FiltrosCampana) filtrosCampana).getStatus() < 3)) {
				q.setParameter("status", Arrays.asList(((FiltrosCampana) filtrosCampana).getStatus()));
			}
			q.setParameter("cve_campana", Arrays.asList(((FiltrosCampana) filtrosCampana).getCveCampana()));
			
			q.executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.getStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
	

	@Override
	public List consultar(Filtros filtrosCampana) throws Exception {
		entityManager.clear();

		Query q = null;
		StringBuffer queryString = new StringBuffer("select c,cl from Campana as c inner join CliPro as cl on (c.cliPro=cl.cveClipro)");

		if (((FiltrosCampana) filtrosCampana).getNombre() != null && ((FiltrosCampana) filtrosCampana).getNombre().length() > 0) {
			queryString.append(" and ");
			queryString.append(" lower(c.nombre) like lower(:campana)");
		}
		queryString.append(" order by fechaalta DESC");
		
		q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
		if (((FiltrosCampana) filtrosCampana).getNombre() != null && ((FiltrosCampana) filtrosCampana).getNombre().length() > 0) {
			String campana = "%"+((FiltrosCampana) filtrosCampana).getNombre()+"%";
			q.setParameter("campana", Arrays.asList(campana));
		}
		
		return q.getResultList();
	}

	@Override
	public void eliminar(Filtros filtrosCampana) throws Exception {
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		Query q = null;
		StringBuffer queryString = new StringBuffer("update Campana set");
		queryString.append(" status = 2 ");
		queryString.append(" where cve_campana = :cve_campana");
		
		try {
			q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
			q.setParameter("cve_campana", Arrays.asList(((FiltrosCampana) filtrosCampana).getCveCampana()));
			
			q.executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.getStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
}
