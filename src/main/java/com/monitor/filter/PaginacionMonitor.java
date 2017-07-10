package com.monitor.filter;

import java.util.List;

public class PaginacionMonitor {
	private int defaultRegistros = 10;
	private static final int DEFAULT_PAGE_INDEX = 1;
	private int records;
	private int recordsTotal;
	private int pageIndex;
	private int pages;
	private List<?> origModel;
	private List<?> model;

	public PaginacionMonitor() {
	}

	public void setModel(List<?> model) {
		this.origModel = model;
		
		this.pageIndex = DEFAULT_PAGE_INDEX;
		this.recordsTotal = model.size();

		updateModel();
	}

	public void updateModel() {
		records = defaultRegistros;
		System.out.println("records " + records);
		if (records > 0) {
			pages = records <= 0 ? 1 : recordsTotal / records;
			System.out.println("pages " + pages);
			if (pages == 0) {
				pages = 1;
				pageIndex = DEFAULT_PAGE_INDEX;
			}else if (recordsTotal % records > 0) {
				System.out.println("recordsTotal % records " + recordsTotal % records);
				pages++;
			}

			
		} else {
			records = 1;
			pages = 1;
			pageIndex = DEFAULT_PAGE_INDEX;
		}
		int fromIndex = getFirst();
		int toIndex = getFirst() + records;

		if (toIndex > this.recordsTotal) {
			toIndex = this.recordsTotal;
		}

		this.model = origModel.subList(fromIndex, toIndex);
	}

	public void next() {
		System.out.println("Entra a next");
		if (this.pageIndex < pages) {
			this.pageIndex++;
		}

		updateModel();
	}

	public void prev() {
		if (this.pageIndex > 1) {
			this.pageIndex--;
		}

		updateModel();
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
		return (pageIndex * records) - records;
	}

	public List<?> getModel() {
		return model;
	}

	public void setPageIndex(int pageIndex) {
		System.out.println("pageIndex" + pageIndex);
		this.pageIndex = pageIndex;

	}

	public String getViewListDetalle() {
		System.out.println("get view listaDetalle");
		return "listaDetalle";
	}

	public String getViewCuadricula() {
		System.out.println("get view cuadriculaView");
		return "cuadriculaView";
	}

	public int getDefaultRegistros() {
		System.out.println("getDefaultRegistros " + defaultRegistros);
		return defaultRegistros;
	}

	public void setDefaultRegistros(int defaultRegistros) {
		System.out.println("setDefaultRegistros " + defaultRegistros);
		this.defaultRegistros = defaultRegistros;
		updateModel();
	}

	public void updateIrPagina() {
		updateModel();

	}
}
