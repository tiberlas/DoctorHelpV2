package com.ftn.dr_help.dto;

import com.ftn.dr_help.model.enums.RoleEnum;

public class LoginResponseDTO {

	/*
	 * when successfully logged in sent id, role, JWT 
	 * */
	
	private String jwtToken;
    private Long expiresIn;
	private Long id;
	private RoleEnum userRole;
	private Boolean mustChangePassword;
	
	public LoginResponseDTO() {
		
	}
	
	public LoginResponseDTO(Long id, RoleEnum userRole, String jwtToken, long expires) {
		super();
		this.id = id;
		this.userRole = userRole;
		this.jwtToken = jwtToken;
		this.expiresIn = expires;
	}
	
	
	public LoginResponseDTO(Long id, RoleEnum userRole, String jwtToken, long expires, Boolean mustChangePassword) {
		super();
		this.id = id;
		this.userRole = userRole;
		this.jwtToken = jwtToken;
		this.expiresIn = expires;
		this.mustChangePassword = mustChangePassword;
	}
//	
	public LoginResponseDTO(LoginResponseDTO loginResponse) {
		super ();
		this.id = loginResponse.id;
		this.userRole = loginResponse.getUserRole();
		this.jwtToken = loginResponse.getJwtToken();
		this.expiresIn = loginResponse.getExpiresIn();
		
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public RoleEnum getUserRole() {
		return userRole;
	}
	
	public void setUserRole(RoleEnum userRole) {
		this.userRole = userRole;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Boolean getMustChangePassword() {
		return mustChangePassword;
	}

	public void setMustChangePassword(Boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}
	
}
