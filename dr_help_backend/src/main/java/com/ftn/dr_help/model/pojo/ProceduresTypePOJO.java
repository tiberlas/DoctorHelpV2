package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "proceduresType")
public class ProceduresTypePOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "isOperation", nullable = false)
	private boolean isOperation;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "duration", nullable = false)
	private  Date duration;
	
	@OneToMany(mappedBy = "procedureType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AppointmentPOJO> appointment;
	
	@Column(name="deleted", nullable= false)
	private boolean deleted;
	
	@OneToMany(mappedBy = "procedurasTypes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RoomPOJO> roomList;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private ClinicPOJO clinic;
	
	@OneToMany(mappedBy = "procedureType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DoctorPOJO> doctors;
	
	public ProceduresTypePOJO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProceduresTypePOJO(Long id, String name, double price, boolean isOperation, Date duration,
			List<AppointmentPOJO> appointment, ClinicPOJO clinic) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.isOperation = isOperation;
		this.duration = duration;
		this.appointment = appointment;
		this.clinic = clinic;
		this.roomList = new ArrayList<RoomPOJO>();
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isOperation() {
		return isOperation;
	}
	public void setOperation(boolean isOperation) {
		this.isOperation = isOperation;
	}
	public Date getDuration() {
		return duration;
	}
	public void setDuration(Date duration) {
		this.duration = duration;
	}
	public ClinicPOJO getClinic() {
		return clinic;
	}
	public void setClinic(ClinicPOJO clinic) {
		this.clinic = clinic;
	}

	public List<RoomPOJO> getRoom() {
		return roomList;
	}

	public void setRoom(List<RoomPOJO> room) {
		this.roomList = room;
	}
	
	public void addRoom(RoomPOJO room) {
		this.roomList.add(room);
	}
	
	public void removeRoom(RoomPOJO room) {
		this.roomList.remove(room);
	}

	public List<AppointmentPOJO> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<AppointmentPOJO> appointment) {
		this.appointment = appointment;
	}

	public List<RoomPOJO> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomPOJO> roomList) {
		this.roomList = roomList;
	}

	public List<DoctorPOJO> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<DoctorPOJO> doctors) {
		this.doctors = doctors;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	

}
