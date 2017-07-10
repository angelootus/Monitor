package com.monitor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PlazaDao {
	private EntityManager entityManager;

	public PlazaDao(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	public List consultaPlazasActivas(String cveClipro) {
		Query q = entityManager.createQuery(
				"SELECT count(f.id.cvePlaza), p.cvePlaza,p.nombre from Plaza as p  LEFT JOIN  Foto as f on (p.cvePlaza = f.id.cvePlaza) where (p.status=1 and p.cveClipro = :cveClipro)group by f.id.cvePlaza,p.cvePlaza,p.nombre order by p.nombre desc");
		q.setParameter("cveClipro", cveClipro);
		return q.getResultList();
	}
	
	public List consultaPlazas() {
//		Query q = entityManager.createQuery("select p from Plaza as p where p.status=1 order by p.nombre desc");
		Query q = entityManager.createQuery("select p from Plaza as p order by p.nombre desc");
		return q.getResultList();
	}
}
