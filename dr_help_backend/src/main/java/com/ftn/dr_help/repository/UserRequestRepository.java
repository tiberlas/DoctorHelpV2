package com.ftn.dr_help.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.UserRequestPOJO;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequestPOJO, Long> {
	
	UserRequestPOJO findByEmail(String email);
	
	UserRequestPOJO findByInsuranceNumber (Long insuranceNumber);
	
}
