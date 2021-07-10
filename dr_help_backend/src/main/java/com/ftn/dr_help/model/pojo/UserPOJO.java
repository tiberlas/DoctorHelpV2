package com.ftn.dr_help.model.pojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.dr_help.model.enums.RoleEnum;

public class UserPOJO implements UserDetails{

	/*
	 * base for JWT 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String address;
	private String city;
	private String state;
	private String phoneNumber;
	private Calendar birthday;
	private Boolean mustChangePassword = false;
	private Boolean enabled = true;
	private List<Authority> authorities;//for jwt it's required to be a list but we only have one element in the list
	
	public UserPOJO(Long id, String firstName, String lastName, String email, String password, String address, String city,
			String state, String phoneNumber, Calendar birthday, RoleEnum role, Boolean enabled) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
		this.birthday = birthday;
		this.authorities = new ArrayList<Authority>();
		this.authorities.add(new Authority(role));
		this.enabled = enabled;
	}
	
	
	
	public UserPOJO(Long id, String firstName, String lastName, String email, String password, String address, String city,
			String state, String phoneNumber, Calendar birthday, RoleEnum role, Boolean mustChangePassword, Boolean enabled) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
		this.birthday = birthday;
		this.authorities = new ArrayList<Authority>();
		this.authorities.add(new Authority(role));
		this.mustChangePassword = mustChangePassword;
		this.enabled = enabled;
	}
	
	public UserPOJO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Calendar getBirthday() {
		return birthday;
	}
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	//gets the role from authorities
	//this format is required for front end
	public RoleEnum getRole() {
		if(this.authorities.size() != 0) {
			return this.authorities.get(0).getRole();
		}
		
		return RoleEnum.UNREGISTERED;
	}
	
	@JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	/*
	 * account is enabled or active
	 * */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public Boolean getMustChangePassword() {
		return mustChangePassword;
	}

	public void setMustChangePassword(Boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}

	
	
}
