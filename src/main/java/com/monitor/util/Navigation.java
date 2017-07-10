package com.monitor.util;

import com.monitor.filter.Filtros;

public interface Navigation {
	public void next();
	public void prev();
	public void irA();
	public void busqueda();
	public void update(Filtros filtros);
	public void eliminar();
	public void actualizar();
}
