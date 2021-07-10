package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.pojo.ClinicPOJO;

public class ClinicPreviewDTO {

	private Long id;
	private String name = "/"; 
	private String address = "/";
	private String city = "/";
	private String state = "/";
	private String rating = "/";
	private String price = "/";

	public ClinicPreviewDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ClinicPreviewDTO(Long id, String name, String address, String city, String state, String rating, String price) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.rating = rating;
		this.price = price;
	}
	
	public ClinicPreviewDTO (ClinicPOJO clinic) {
		this.id = clinic.getId();
		this.name = clinic.getName();
		this.address = clinic.getAddress();
		this.city = clinic.getCity();
		this.state = clinic.getState();
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
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
