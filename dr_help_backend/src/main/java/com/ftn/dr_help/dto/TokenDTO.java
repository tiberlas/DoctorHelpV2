package com.ftn.dr_help.dto;

public class TokenDTO {

	private String jwtToken;
	
	public TokenDTO() {
		
	}
	
	public TokenDTO(String token) {
		super();
		this.jwtToken = token;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
}
