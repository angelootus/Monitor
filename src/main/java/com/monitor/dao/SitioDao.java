package com.monitor.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.filter.Filtros;
import com.monitor.filter.FiltrosCampana;
import com.monitor.filter.FiltrosSitios;
import com.monitor.filter.FiltrosUsuario;
import com.monitor.model.CliPro;

public class SitioDao implements MonitorDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SitioDao.class);
	private EntityManager entityManager;

	public SitioDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List consultaSitiosActivos(String cveClipro) {
		Query q = entityManager.createQuery(
				"SELECT count(f.id.cveSitio), s.id.cveSitio,s.ubicacion from Sitio as s LEFT JOIN  Foto as f ON (s.id.cveSitio = f.id.cveSitio)  WHERE (s.status=1 and s.id.cveClipro=:cveClipro) group by f.id.cveSitio,s.id.cveSitio,s.ubicacion order by s.ubicacion desc");
		q.setParameter("cveClipro", cveClipro);
		return q.getResultList();
	}
	
	@Override
	public List consultar(Filtros filtrosSitios) throws Exception {
		entityManager.clear();

		Query q = null;
		StringBuffer queryString = new StringBuffer("select cl, s, c, p");
		queryString.append(" from CliPro cl ");
		queryString.append(" inner join Campana as c on cl.cveClipro = c.id.cveClipro");
		queryString.append(" inner join Sitio as s on s.id.cveClipro = c.id.cveClipro and s.id.cveCampana = c.id.cveCampana");
		queryString.append(" inner join Plaza as p on p.cvePlaza= s.id.cvePlaza");
		queryString.append(" where 1=1 ");

		if (((FiltrosSitios) filtrosSitios).getCveSitio() != null && ((FiltrosSitios) filtrosSitios).getCveSitio().length() > 0) {
			queryString.append(" and ");
			queryString.append(" lower(s.id.cveSitio) like lower(:cve_sitio) ");
		}
		if (((FiltrosSitios) filtrosSitios).getCvePlaza() != null && ((FiltrosSitios) filtrosSitios).getCvePlaza().length() > 0) {
			queryString.append(" and ");
			queryString.append(" lower(s.id.cvePlaza) like lower(:cve_plaza) ");
		}
		if (((FiltrosSitios) filtrosSitios).getCveCampana() != null && ((FiltrosSitios) filtrosSitios).getCveCampana().length() > 0) {
			queryString.append(" and ");
			queryString.append(" lower(s.id.cveCampana) like lower(:cve_campana) ");
		}
		
		queryString.append(" order by s.id.cveSitio");

		q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);

		if (((FiltrosSitios) filtrosSitios).getCveSitio() != null && ((FiltrosSitios) filtrosSitios).getCveSitio().length() > 0) {
			String cve_sitio = "%"+((FiltrosSitios) filtrosSitios).getCveSitio()+"%";
			q.setParameter("cve_sitio", Arrays.asList(cve_sitio));
		}
		if (((FiltrosSitios) filtrosSitios).getCvePlaza() != null && ((FiltrosSitios) filtrosSitios).getCvePlaza().length() > 0) {
			String cve_plaza = "%"+((FiltrosSitios) filtrosSitios).getCvePlaza()+"%";
			q.setParameter("cve_plaza", Arrays.asList(cve_plaza));
		}
		if (((FiltrosSitios) filtrosSitios).getCveCampana() != null && ((FiltrosSitios) filtrosSitios).getCveCampana().length() > 0) {
			String cve_campana = "%"+((FiltrosSitios) filtrosSitios).getCveCampana()+"%";
			q.setParameter("cve_campana", Arrays.asList(cve_campana));
		}
		
		return q.getResultList();
	}

	public List consultarTree(Filtros filtrosSitios) throws Exception {
		entityManager.clear();
		
		Query q = null;
		StringBuffer queryString = new StringBuffer("select max(c.id.cveClipro) as cveClipro, max(c.nombre) as clipro, max(p.cvePlaza) as cvePlaza, max(p.nombre) as plaza, ");
		queryString.append(" max(c.id.cveCampana) as cveCampana, max(c.nombre) as campana, max(s.id.cveSitio) as cveSitio, count(*) as num_rows");
		queryString.append(" from CliPro cl ");
		queryString.append(" inner join Campana as c on cl.cveClipro = c.id.cveClipro");
		queryString.append(" inner join Sitio as s on s.id.cveClipro = c.id.cveClipro and s.id.cveCampana = c.id.cveCampana");
		queryString.append(" inner join Plaza as p on p.cvePlaza=s.id.cvePlaza");
		
		if (((FiltrosSitios) filtrosSitios).getCveClipro() != null && ((FiltrosSitios) filtrosSitios).getCveClipro().length() > 0) {
			queryString.append(" where ");
			queryString.append(" lower(c.id.cveClipro) like lower(:cliente) ");
		}
		queryString.append(" group by");

		if ( ((FiltrosSitios) filtrosSitios).getOrden() != null && ( ((FiltrosSitios) filtrosSitios).getOrden() >= 0) && ((FiltrosSitios) filtrosSitios).getOrden() <= 2 )  {
			Integer order = ((FiltrosSitios) filtrosSitios).getOrden();
			
			switch (order) {
			case 1:
				queryString.append(" p.cvePlaza,");
				break;
			case 2:
				queryString.append(" c.id.cveCampana,");
				break;
			default:
				queryString.append(" p.cvePlaza,");
				break;
			}
		}
		queryString.append(" s.id.cveSitio");
		queryString.append(" order by max(s.id.cveSitio)");
		
		q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
		if (((FiltrosSitios) filtrosSitios).getCveClipro() != null && ((FiltrosSitios) filtrosSitios).getCveClipro().length() > 0) {
			CliPro clipro = new CliPro();
			clipro.setCveClipro(((FiltrosSitios) filtrosSitios).getCveClipro()+"%");
			q.setParameter("cliente", Arrays.asList(clipro));
		}
		
		return q.getResultList();
	}
	
	@Override
	public void eliminar(Filtros filtrosSitios) throws Exception {
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		Query q = null;
		StringBuffer queryString = new StringBuffer("update Sitio set");
		queryString.append(" status = 2 ");
		queryString.append(" where cve_sitio = :cve_sitio");
		
		try {
			q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
			q.setParameter("cve_sitio", Arrays.asList(((FiltrosSitios) filtrosSitios).getCveSitio()));
			
			q.executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.getStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	@Override
	public void actualizar(Filtros filtrosSitios) throws Exception {
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		Query q = null;
		StringBuffer queryString = new StringBuffer("update Sitio set");
		
		if (((FiltrosSitios) filtrosSitios).getUbicacion()!= null && ((FiltrosSitios) filtrosSitios).getUbicacion().length() > 0) {
			queryString.append(" ubicacion = :ubicacion,");
		}
		if (((FiltrosSitios) filtrosSitios).getIluminacion()!= null && (((FiltrosSitios) filtrosSitios).getIluminacion() > 0 && ((FiltrosSitios) filtrosSitios).getIluminacion() < 3)) {
			queryString.append(" iluminacion = :iluminacion,");
		}
		if (((FiltrosSitios) filtrosSitios).getStatus()!= null && (((FiltrosSitios) filtrosSitios).getStatus() > 0 && ((FiltrosSitios) filtrosSitios).getStatus() < 3)) {
			queryString.append(" status = :status, ");
		}
		if (((FiltrosSitios) filtrosSitios).getCveCampana()!= null && ((FiltrosSitios) filtrosSitios).getCveCampana().length() > 0) {
			queryString.append(" id.cveCampana = :cve_campana, ");
		}
		if (((FiltrosSitios) filtrosSitios).getCvePlaza()!= null && ((FiltrosSitios) filtrosSitios).getCvePlaza().length() > 0) {
			queryString.append(" id.cvePlaza = :cve_plaza ");
		}
		queryString.append(" where cve_sitio = :cve_sitio");
		
		try {
			q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
			
			if (((FiltrosSitios) filtrosSitios).getUbicacion()!= null && ((FiltrosSitios) filtrosSitios).getUbicacion().length() > 0) {
				q.setParameter("ubicacion", Arrays.asList(((FiltrosSitios) filtrosSitios).getUbicacion()));
			}
			if (((FiltrosSitios) filtrosSitios).getIluminacion()!= null && (((FiltrosSitios) filtrosSitios).getIluminacion() > 0 && ((FiltrosSitios) filtrosSitios).getIluminacion() < 3)) {
				q.setParameter("iluminacion", Arrays.asList(((FiltrosSitios) filtrosSitios).getIluminacion()));
			}
			if (((FiltrosSitios) filtrosSitios).getStatus()!= null && (((FiltrosSitios) filtrosSitios).getStatus() > 0 && ((FiltrosSitios) filtrosSitios).getStatus() < 3)) {
				q.setParameter("status", Arrays.asList(((FiltrosSitios) filtrosSitios).getStatus()));
			}
			if (((FiltrosSitios) filtrosSitios).getCveCampana()!= null && ((FiltrosSitios) filtrosSitios).getCveCampana().length() > 0) {
				q.setParameter("cve_campana", Arrays.asList(((FiltrosSitios) filtrosSitios).getCveCampana()));
			}
			if (((FiltrosSitios) filtrosSitios).getCvePlaza()!= null && ((FiltrosSitios) filtrosSitios).getCvePlaza().length() > 0) {
				q.setParameter("cve_plaza", Arrays.asList(((FiltrosSitios) filtrosSitios).getCvePlaza()));
			}
			q.setParameter("cve_sitio", Arrays.asList(((FiltrosSitios) filtrosSitios).getCveSitio()));
			
			q.executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.getStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
	
}
