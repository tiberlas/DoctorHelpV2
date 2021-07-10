package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "room")
public class RoomPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "number", nullable = false)
	private int number;
	
	@Column(name="deleted", nullable= false)
	private boolean deleted;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	ProceduresTypePOJO procedurasTypes;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ClinicPOJO clinic;
	
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AppointmentPOJO> appointmentList;
	
	public RoomPOJO() {
		super();
		this.deleted = false;
		this.appointmentList = new ArrayList<AppointmentPOJO>();
	}
	
	public RoomPOJO(Long id, @NotBlank String name, @NotBlank int number, ProceduresTypePOJO procedurasTypes, ClinicPOJO clinic) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.deleted = false;
		this.procedurasTypes = procedurasTypes;
		this.clinic = clinic;
		this.appointmentList = new ArrayList<AppointmentPOJO>();
	}

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public List<AppointmentPOJO> getAppointments() {
		return appointmentList;
	}
	public void setAppointments(List<AppointmentPOJO> appointments) {
		this.appointmentList = appointments;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public ProceduresTypePOJO getProcedurasTypes() {
		return procedurasTypes;
	}
	public void setProcedurasTypes(ProceduresTypePOJO procedurasTypes) {
		this.procedurasTypes = procedurasTypes;
	}
	public ClinicPOJO getClinic() {
		return clinic;
	}
	public void setClinic(ClinicPOJO clinic) {
		this.clinic = clinic;
	}
	
	public void addAppointment(AppointmentPOJO appointment) {
		this.appointmentList.add(appointment);
	}
	
	public void removeAppointment(AppointmentPOJO appointment) {
		this.appointmentList.remove(appointment);
	}

}
