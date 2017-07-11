package com.monitor.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.filter.Filtros;
import com.monitor.filter.FiltrosUsuario;
import com.monitor.model.CliPro;
import com.monitor.model.Usuario;

public class UsuarioDao implements MonitorDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDao.class);
	private EntityManager entityManager;

	public UsuarioDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List consultar(Filtros filtrosUsuario) throws Exception {
		entityManager.clear();

		Query q = null;
		StringBuffer queryString = new StringBuffer("select u,cl from Usuario as u inner join CliPro as cl on (u.id.cliPro=cl.cveClipro)");

		if (((FiltrosUsuario) filtrosUsuario).getNombre() != null && ((FiltrosUsuario) filtrosUsuario).getNombre().length() > 0) {
			queryString.append(" and ");
			queryString.append(" lower(u.nombre) like lower(:nombre)");
		}
		queryString.append(" order by fechaalta DESC");
		
		q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
		if (((FiltrosUsuario) filtrosUsuario).getNombre() != null && ((FiltrosUsuario) filtrosUsuario).getNombre().length() > 0) {
			String nombre = "%"+((FiltrosUsuario) filtrosUsuario).getNombre()+"%";
			q.setParameter("nombre", Arrays.asList(nombre));
		}
		return q.getResultList();
	}
	
	public void insertar(Usuario usuario){
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		try {
			entityManager.persist(usuario);
		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.error(e.getMessage());
			entityManager.getTransaction().rollback();
		}
	}


	public void eliminar(Filtros filtrosUsuario) throws Exception {
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		Query q = null;
//		StringBuffer queryString = new StringBuffer("delete from Usuario where email = :email");
		StringBuffer queryString = new StringBuffer("update Usuario set status=2 where email = :email");
		
		try {
			q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
			q.setParameter("email", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getEmail()));
			q.executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.error(e.getMessage());
			entityManager.getTransaction().rollback();
		}
	}



	public void actualizar(Filtros filtrosUsuario) throws Exception {
		entityManager.clear();
		entityManager.getTransaction().begin();
		
		Query q = null;
		StringBuffer queryString = new StringBuffer("update Usuario set");
		
//		if (((FiltrosUsuario) filtrosUsuario).getEmail()!= null && ((FiltrosUsuario) filtrosUsuario).getEmail().length() > 0) {
//			queryString.append(" email = :email,");
//		}
		if (((FiltrosUsuario) filtrosUsuario).getNombre()!= null && ((FiltrosUsuario) filtrosUsuario).getNombre().length() > 0) {
			queryString.append(" nombre = :nombre,");
		}
		if (((FiltrosUsuario) filtrosUsuario).getApellidos()!= null && ((FiltrosUsuario) filtrosUsuario).getApellidos().length() > 0) {
			queryString.append(" apellidos = :apellidos,");
		}
		if (((FiltrosUsuario) filtrosUsuario).getContrasena()!= null && ((FiltrosUsuario) filtrosUsuario).getContrasena().length() > 0) {
			queryString.append(" contrasena = :contrasena,");
		}
		if (((FiltrosUsuario) filtrosUsuario).getFechaAlta()!= null) {
			queryString.append(" fechaalta = :fechaalta,");
		}
		if (((FiltrosUsuario) filtrosUsuario).getTipo()!= null && (((FiltrosUsuario) filtrosUsuario).getTipo() > 0 && ((FiltrosUsuario) filtrosUsuario).getTipo() < 4)) {
			queryString.append(" tipo = :tipo,");
		}
		if (((FiltrosUsuario) filtrosUsuario).getStatus()!= null && (((FiltrosUsuario) filtrosUsuario).getStatus() > 0 && ((FiltrosUsuario) filtrosUsuario).getStatus() < 3)) {
			queryString.append(" status = :status ");
		}
		queryString.append(" where email = :user_email");
		
		try {
			q = entityManager.createQuery(queryString.toString()).setHint("org.hibernate.cacheable", Boolean.FALSE);
//			if (((FiltrosUsuario) filtrosUsuario).getEmail() != null && ((FiltrosUsuario) filtrosUsuario).getEmail().length() > 0) {
//				q.setParameter("email", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getEmail()));
//			}
			if (((FiltrosUsuario) filtrosUsuario).getNombre() != null && ((FiltrosUsuario) filtrosUsuario).getNombre().length() > 0) {
				q.setParameter("nombre", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getNombre()));
			}
			if (((FiltrosUsuario) filtrosUsuario).getApellidos() != null && ((FiltrosUsuario) filtrosUsuario).getApellidos().length() > 0) {
				q.setParameter("apellidos", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getApellidos()));
			}
			if (((FiltrosUsuario) filtrosUsuario).getContrasena() != null && ((FiltrosUsuario) filtrosUsuario).getContrasena().length() > 0) {
				q.setParameter("contrasena", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getContrasena()));
			}
			if (((FiltrosUsuario) filtrosUsuario).getFechaAlta() != null) {
				q.setParameter("fechaalta", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getFechaAlta()));
			}
			if (((FiltrosUsuario) filtrosUsuario).getTipo()!= null && (((FiltrosUsuario) filtrosUsuario).getTipo() > 0 && ((FiltrosUsuario) filtrosUsuario).getTipo() < 4)) {
				q.setParameter("tipo", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getTipo()));
			}
			if (((FiltrosUsuario) filtrosUsuario).getStatus()!= null && (((FiltrosUsuario) filtrosUsuario).getStatus() > 0 && ((FiltrosUsuario) filtrosUsuario).getStatus() < 3)) {
				q.setParameter("status", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getStatus()));
			}
			q.setParameter("user_email", Arrays.asList(((FiltrosUsuario) filtrosUsuario).getEmail()));
			
			q.executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.getStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public List consultaLoginPorEmail(String email) {
		Query q = entityManager.createQuery("SELECT m from Usuario  m where m.email=:email");
		q.setParameter("email", email);
		return q.getResultList();
	}
}
