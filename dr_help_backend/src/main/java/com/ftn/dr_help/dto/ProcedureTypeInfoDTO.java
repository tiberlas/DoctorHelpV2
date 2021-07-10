package com.ftn.dr_help.dto;

public class ProcedureTypeInfoDTO {

	private Long id;
	private String name;
	private double price;
	
	public ProcedureTypeInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProcedureTypeInfoDTO(Long id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
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
	
	
	
}
