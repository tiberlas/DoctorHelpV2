package com.ftn.dr_help.dto;

public class RoomSearchDTO {
	
	private String name;
	private Long number;
	private Long typeId;
	private String date;
	
	public RoomSearchDTO() {
		super();
	}
	
	public RoomSearchDTO(String name, Long number, Long typeId, String date) {
		super();
		this.name = name;
		this.number = number;
		this.typeId = typeId;
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
