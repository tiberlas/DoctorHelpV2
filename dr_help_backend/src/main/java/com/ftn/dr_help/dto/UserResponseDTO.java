package com.ftn.dr_help.dto;

public class UserResponseDTO {

	private String answer; 
	
	public UserResponseDTO () {}

	public UserResponseDTO (String response) {
		this.answer = response;
	}

	public String getResponse() {
		return answer;
	}

	public void setResponse(String response) {
		this.answer = response;
	}
	
	
}
