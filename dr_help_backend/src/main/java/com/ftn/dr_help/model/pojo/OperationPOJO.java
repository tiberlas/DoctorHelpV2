package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ftn.dr_help.model.enums.OperationStatus;

@Entity
@Table (name = "operations")
public class OperationPOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private PatientPOJO patient;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private DoctorPOJO requestedDoctor;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private DoctorPOJO firstDoctor;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private DoctorPOJO secondDoctor;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private DoctorPOJO thirdDoctor;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RoomPOJO room;
	
	@OneToOne (fetch = FetchType.LAZY)
	private ProceduresTypePOJO operationType;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "status", nullable = false)
	private OperationStatus status;
	
	@Version
	private Long version;
	
	private boolean deleted;
	
	public OperationPOJO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public PatientPOJO getPatient() {
		return patient;
	}

	public void setPatient(PatientPOJO patient) {
		this.patient = patient;
	}

	public DoctorPOJO getFirstDoctor() {
		return firstDoctor;
	}

	public void setFirstDoctor(DoctorPOJO firstDoctor) {
		this.firstDoctor = firstDoctor;
	}

	public DoctorPOJO getSecondDoctor() {
		return secondDoctor;
	}

	public void setSecondDoctor(DoctorPOJO secondDoctor) {
		this.secondDoctor = secondDoctor;
	}

	public DoctorPOJO getThirdDoctor() {
		return thirdDoctor;
	}

	public void setThirdDoctor(DoctorPOJO thirdDoctor) {
		this.thirdDoctor = thirdDoctor;
	}

	public RoomPOJO getRoom() {
		return room;
	}

	public void setRoom(RoomPOJO room) {
		this.room = room;
	}

	public ProceduresTypePOJO getOperationType() {
		return operationType;
	}

	public void setOperationType(ProceduresTypePOJO operationType) {
		this.operationType = operationType;
	}

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public DoctorPOJO getRequestedDoctor() {
		return requestedDoctor;
	}

	public void setRequestedDoctor(DoctorPOJO requestedDoctor) {
		this.requestedDoctor = requestedDoctor;
	}
	
}
