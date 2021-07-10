package com.ftn.dr_help.model.pojo;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.dr_help.model.enums.RoleEnum;

public class Authority implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	private RoleEnum role;
	
	public Authority(RoleEnum role) {
		super();
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role.toString();
	}

	@JsonIgnore
	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}
	

}
