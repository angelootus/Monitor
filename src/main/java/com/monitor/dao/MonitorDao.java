package com.monitor.dao;

import java.util.List;

import com.monitor.filter.Filtros;

public interface MonitorDao {
	public List consultar(Filtros filtros) throws Exception;
	public void eliminar(Filtros filtros) throws Exception;
	public void actualizar(Filtros filtros) throws Exception;
}
