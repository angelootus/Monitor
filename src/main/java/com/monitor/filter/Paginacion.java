package com.monitor.filter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitor.dao.UsuarioDao;

public class Paginacion {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDao.class);
	private int defaultRegistros = 10;
	private static final int DEFAULT_PAGE_INDEX = 0;
	private int records;
	private int recordsTotal;
	private int pageIndex;
	private int pages;
	private int irA;
	private List<?> origModel;
	private List<?> model;

	public Paginacion() {
		this.pageIndex = DEFAULT_PAGE_INDEX;
	}

	public void setModel(List<?> model) {
		this.origModel = model;
		this.records = model.size();
		this.recordsTotal = model.size();
	}

	public void next() {
		LOGGER.debug("Entra a next");
		if (this.pageIndex < recordsTotal-1) {
			this.pageIndex++;
		}
	}

	public void prev() {
		LOGGER.debug("Entra a prev");
		if (this.pageIndex > 0) {
			this.pageIndex--;
		}
	}

	public int getRecords() {
		return records;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPages() {
		return pages;
	}

	public int getFirst() {
		return ((pageIndex * records) - records) < 0 ? 0 : (pageIndex * records) - records ;
	}

	public List<?> getModel() {
		return model;
	}

	public void setPageIndex(int pageIndex) {
		LOGGER.debug("pageIndex" + pageIndex);
		this.pageIndex = pageIndex;
	}

	public int getDefaultRegistros() {
		LOGGER.debug("getDefaultRegistros " + defaultRegistros);
		return defaultRegistros;
	}

	public void setDefaultRegistros(int defaultRegistros) {
		LOGGER.debug("setDefaultRegistros " + defaultRegistros);
		this.defaultRegistros = defaultRegistros;
		records = defaultRegistros;
	}

	public int getIrA() {
		return irA;
	}

	public void setIrA(int irA) {
		this.irA = irA;
	}
}
