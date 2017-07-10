package com.monitor.dao;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.monitor.filter.FiltrosMonitor;

public class FotoDao {
	private EntityManager entityManager;

	public FotoDao(EntityManager entityManager) {
		this.entityManager = entityManager;
		// TODO Auto-generated constructor stub
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List obtenerFotosPorUsuario(FiltrosMonitor filtrosMonitor) throws Exception {
		entityManager.clear();

		Query q = null;
		StringBuffer queryString = new StringBuffer(
				"select f from Foto as f inner join Usuario as u on (f.id.cveClipro= u.cliPro.cveClipro) where f.id.cveClipro=:cveClipro");

		if (filtrosMonitor.getFilterCampana() != null && filtrosMonitor.getFilterCampana().length > 0) {
			queryString.append(" and ");
			queryString.append(" f.id.cveCampana in  :campana");
		}
		if (filtrosMonitor.getFilterPlaza() != null && filtrosMonitor.getFilterPlaza().length > 0) {
			queryString.append(" and ");
			queryString.append(" f.id.cvePlaza in  :plaza");
		}
		if (filtrosMonitor.getFilterSitio() != null && filtrosMonitor.getFilterSitio().length > 0) {
			queryString.append(" and ");
			queryString.append(" f.id.cveSitio in  :sitio");
		}

		if (filtrosMonitor.getFechaInicio() != null && filtrosMonitor.getFechaFin() != null) {
			queryString.append(" and (");
			queryString.append("( f.id.fechaHora >= :fechaInicio ) ");
			queryString.append(" and ");
			queryString.append("( f.id.fechaHora <= :fechaFin )");
			queryString.append(")");
		}

		if (filtrosMonitor.getFechaInicio() != null && filtrosMonitor.getFechaFin() == null) {
			queryString.append(" and ");
			queryString.append(" f.id.fechaHora>= :fechaInicio  ");
		}
		if (filtrosMonitor.getFechaInicio() == null && filtrosMonitor.getFechaFin() != null) {
			queryString.append(" and ");
			queryString.append(" f.id.fechaHora <= :fechaFin ");
		}

		if (filtrosMonitor.getOrdenarPor() != null && !filtrosMonitor.getOrdenarPor().isEmpty()) {
			queryString.append(" order by ");
			switch (filtrosMonitor.getOrdenarPor()) {
			case "Plaza":
				queryString.append(" f.id.cvePlaza ");
				break;
			case "Sitio":
				queryString.append(" f.id.cveSitio ");
				break;
			case "Campana":
				queryString.append(" f.id.cveCampana ");
				break;
			case "Fecha":
				queryString.append(" f.id.fechaHora ");
				break;

			default:
				break;
			}

		}

		q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
		q.setParameter("cveClipro", filtrosMonitor.getCveClipro());
		if (filtrosMonitor.getFilterCampana() != null && filtrosMonitor.getFilterCampana().length > 0) {		
			q.setParameter("campana", Arrays.asList(filtrosMonitor.getFilterCampana()));
		}

		if (filtrosMonitor.getFilterPlaza() != null && filtrosMonitor.getFilterPlaza().length > 0) {
			q.setParameter("plaza", Arrays.asList(filtrosMonitor.getFilterPlaza()));
		}
		if (filtrosMonitor.getFilterSitio() != null && filtrosMonitor.getFilterSitio().length > 0) {
		
			q.setParameter("sitio", Arrays.asList(filtrosMonitor.getFilterSitio()));
		}
		if (filtrosMonitor.getFechaInicio() != null && filtrosMonitor.getFechaFin() != null) {
			q.setParameter("fechaInicio", filtrosMonitor.getFechaInicio());
			q.setParameter("fechaFin", filtrosMonitor.getFechaFin());
		}
		if (filtrosMonitor.getFechaInicio() != null && filtrosMonitor.getFechaFin() == null) {
			q.setParameter("fechaInicio", filtrosMonitor.getFechaInicio());
		}
		if (filtrosMonitor.getFechaInicio() == null && filtrosMonitor.getFechaFin() != null) {
			q.setParameter("fechaFin", filtrosMonitor.getFechaFin());
		}

		return q.getResultList();
	}

}
