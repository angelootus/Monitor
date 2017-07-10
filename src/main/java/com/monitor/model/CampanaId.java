package com.monitor.model;
// Generated 23/06/2017 12:40:03 PM by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CampanaId generated by hbm2java
 */
@Embeddable
public class CampanaId implements java.io.Serializable {

	private String cveCampana;
	private String cveClipro;

	public CampanaId() {
	}

	public CampanaId(String cveCampana, String cveClipro) {
		this.cveCampana = cveCampana;
		this.cveClipro = cveClipro;
	}

	@Column(name = "CVE_CAMPANA", nullable = false, length = 12)
	public String getCveCampana() {
		return this.cveCampana;
	}

	public void setCveCampana(String cveCampana) {
		this.cveCampana = cveCampana;
	}

	@Column(name = "CVE_CLIPRO", nullable = false, length = 10)
	public String getCveClipro() {
		return this.cveClipro;
	}

	public void setCveClipro(String cveClipro) {
		this.cveClipro = cveClipro;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CampanaId))
			return false;
		CampanaId castOther = (CampanaId) other;

		return ((this.getCveCampana() == castOther.getCveCampana()) || (this.getCveCampana() != null
				&& castOther.getCveCampana() != null && this.getCveCampana().equals(castOther.getCveCampana())))
				&& ((this.getCveClipro() == castOther.getCveClipro()) || (this.getCveClipro() != null
						&& castOther.getCveClipro() != null && this.getCveClipro().equals(castOther.getCveClipro())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCveCampana() == null ? 0 : this.getCveCampana().hashCode());
		result = 37 * result + (getCveClipro() == null ? 0 : this.getCveClipro().hashCode());
		return result;
	}

}