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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Entity
@Table(name = "clinic")
public class ClinicPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "name", nullable = false)
	private String name;
	
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "state", nullable = false)
	private String state;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<ClinicAdministratorPOJO> clinicAdminList;
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<NursePOJO> nurseList;
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<DoctorPOJO> doctorList;
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<RoomPOJO> roomList;
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<ExaminationReportPOJO> reportList;
	
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<ProceduresTypePOJO> procedureTypesList;
	
	
	public ClinicPOJO() {
		clinicAdminList = new ArrayList<ClinicAdministratorPOJO>();
		nurseList = new ArrayList<NursePOJO>();
		doctorList = new ArrayList<DoctorPOJO> ();
		roomList = new ArrayList<RoomPOJO>();
		reportList = new ArrayList<ExaminationReportPOJO> ();
		procedureTypesList = new  ArrayList<ProceduresTypePOJO>();
		
	}
	
//	@Autowired
//	public ClinicPOJO(@Lazy listClinicAdministrator<ClinicAdministratorPOJO> list) {
//		this.clinicAdminList = list;
//		
//	}


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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<ClinicAdministratorPOJO> getClinicAdminList() {
		return clinicAdminList;
	}


	@Autowired
	public void setClinicAdminList(
			List<ClinicAdministratorPOJO> clinicAdminList) {
		this.clinicAdminList = clinicAdminList;
	}


	public List<NursePOJO> getNurseList() {
		return nurseList;
	}


	public void setNurseList(ArrayList<NursePOJO> nurseList) {
		this.nurseList = nurseList;
	}


	public List<DoctorPOJO> getDoctorList() {
		return doctorList;
	}


	public void setDoctorList(ArrayList<DoctorPOJO> doctorList) {
		this.doctorList = doctorList;
	}


	public List<RoomPOJO> getRoomList() {
		return roomList;
	}


	public void setRoomList(ArrayList<RoomPOJO> roomList) {
		this.roomList = roomList;
	}


	public List<ExaminationReportPOJO> getReportList() {
		return reportList;
	}


	public void setReportList(ArrayList<ExaminationReportPOJO> reportList) {
		this.reportList = reportList;
	}


	public List<ProceduresTypePOJO> getProcedureTypesList() {
		return procedureTypesList;
	}


	public void setProcedureTypesList(
			ArrayList<ProceduresTypePOJO> procedureTypesList) {
		this.procedureTypesList = procedureTypesList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public void addRoom(RoomPOJO room) {
		this.roomList.add(room);
	}
		
	public void deleteRoom(RoomPOJO room) {
		this.roomList.remove(room);
	}
	
}