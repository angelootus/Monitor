package com.monitor.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.monitor.model.Entidad;

@ManagedBean(name="persistencia")
@ApplicationScoped
public class Persistencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static EntityManager entityManager;

	public Persistencia() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Monitor");
		entityManager = emf.createEntityManager();
	}

	@SuppressWarnings("rawtypes")
	public List busqueda(Entidad entidad) {
		Query query = entidad.traerQuery(entityManager);
		List datos = query.getResultList();
		return datos;
	}

	@SuppressWarnings("rawtypes")
	public Entidad busquedaUnitaria(Entidad entidad) {

		Query query = entidad.traerQuery(entityManager);
		query.setMaxResults(1);
		
		List datos = query.getResultList();

		if (datos.size() == 0) {
			return null;
		}

		Entidad e = (Entidad) datos.get(0);

		return e;
	}

	public Entidad insertar(Entidad entidad) {
		try 
		{
			entityManager.getTransaction().begin();
			entityManager.persist(entidad);
			entityManager.getTransaction().commit();
			 	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		 return entidad;

	}

	public Entidad cambiar(Entidad entidad)
	{
		entidad.consultaPorId();
		Entidad  entidadUpdate = busquedaUnitaria(entidad);
		entityManager.getTransaction().begin();
		 entidadUpdate.copiaDatos(entidad);
		entityManager.getTransaction().commit();
		entidad= busquedaUnitaria(entidad);
		return entidad;
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}

	public static void setEntityManager(EntityManager entityManager) {
		Persistencia.entityManager = entityManager;
	}

}
