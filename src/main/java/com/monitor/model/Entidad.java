package com.monitor.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@SuppressWarnings("serial")
public abstract class  Entidad implements Serializable{
	
	protected String consulta;
	
	protected LinkedHashMap<String, Object> parametros;
	
	public abstract Query traerQuery(EntityManager entityManager);
	
	public abstract void  copiaDatos(Entidad entidad);
	
	
	public void consultaPorId()
	{
		consulta="consultaPorId";
		
	}
	
	protected abstract Query consultaPorId(EntityManager entityManager);
	
	public Entidad ()
	{
		parametros= new LinkedHashMap<String, Object>();
	}
	
	

	

}
