package com.ftn.dr_help.model.pojo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ftn.dr_help.model.enums.AppointmentStateEnum;

@Entity
@Table (name = "appointments")
public class AppointmentPOJO implements Serializable{

	public AppointmentPOJO(Long id, Calendar date, DoctorPOJO doctor, NursePOJO nurse, PatientPOJO patient,
			AppointmentStateEnum status, double discount, ProceduresTypePOJO procedureType, RoomPOJO room,
			ExaminationReportPOJO examinationReport, List<DoctorRequestedAppointmentPOJO> requested, boolean deleted,
			Long version) {
		super();
		this.id = id;
		this.date = date;
		this.doctor = doctor;
		this.nurse = nurse;
		this.patient = patient;
		this.status = status;
		this.discount = discount;
		this.procedureType = procedureType;
		this.room = room;
		this.examinationReport = examinationReport;
		this.requested = requested;
		this.deleted = deleted;
		this.version = version;
	}
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "date", nullable = false)
	private Calendar date;

	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private DoctorPOJO doctor;
	
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private NursePOJO nurse;
	
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private PatientPOJO patient;

	@Enumerated(EnumType.STRING)
	@Column (name = "status", nullable = false)
	private AppointmentStateEnum status;
	
	@Column (name = "discount")
	private double discount;
	
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private ProceduresTypePOJO procedureType;
	
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	private RoomPOJO room;
	
	@OneToOne(fetch = FetchType.LAZY)
	private ExaminationReportPOJO examinationReport;
	
	@OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DoctorRequestedAppointmentPOJO> requested;
	
	@Column (nullable = false)
	private boolean deleted;
	
	@Version
	@Column(name="version", unique=false, nullable=false)
	private Long version;
	
	public AppointmentPOJO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public DoctorPOJO getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorPOJO doctor) {
		this.doctor = doctor;
	}
	public NursePOJO getNurse() {
		return nurse;
	}
	public void setNurse(NursePOJO nurse) {
		this.nurse = nurse;
	}
	public PatientPOJO getPatient() {
		return patient;
	}
	public void setPatient(PatientPOJO patient) {
		this.patient = patient;
	}
	public AppointmentStateEnum getStatus() {
		return status;
	}
	public void setStatus(AppointmentStateEnum status) {
		this.status = status;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public ProceduresTypePOJO getProcedureType() {
		return procedureType;
	}
	public void setProcedureType(ProceduresTypePOJO procedureType) {
		this.procedureType = procedureType;
	}
	public RoomPOJO getRoom() {
		return room;
	}
	public void setRoom(RoomPOJO room) {
		this.room = room;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ExaminationReportPOJO getExaminationReport() {
		return examinationReport;
	}
	public void setExaminationReport(ExaminationReportPOJO examinationReport) {
		this.examinationReport = examinationReport;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public List<DoctorRequestedAppointmentPOJO> getRequested() {
		return requested;
	}
	public void setRequested(List<DoctorRequestedAppointmentPOJO> requested) {
		this.requested = requested;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
