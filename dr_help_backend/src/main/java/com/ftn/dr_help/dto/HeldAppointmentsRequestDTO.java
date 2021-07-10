package com.ftn.dr_help.dto;

import java.util.Date;

import com.ftn.dr_help.model.enums.HeldAppointmentDetailLevelEnum;

public class HeldAppointmentsRequestDTO {

	private HeldAppointmentDetailLevelEnum detailLvl;
	private Date referentDate;
	
	public HeldAppointmentsRequestDTO() {
		super();
	}

	public HeldAppointmentsRequestDTO(HeldAppointmentDetailLevelEnum detailLvl, Date referentDate) {
		super();
		this.detailLvl = detailLvl;
		this.referentDate = referentDate;
	}

	public HeldAppointmentDetailLevelEnum getDetailLvl() {
		return detailLvl;
	}

	public void setDetailLvl(HeldAppointmentDetailLevelEnum detailLvl) {
		this.detailLvl = detailLvl;
	}

	public Date getReferentDate() {
		return referentDate;
	}

	public void setReferentDate(Date referentDate) {
		this.referentDate = referentDate;
	}
	
}
