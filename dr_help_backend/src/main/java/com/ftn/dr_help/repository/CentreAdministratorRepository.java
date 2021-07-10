package com.ftn.dr_help.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.dr_help.model.pojo.CentreAdministratorPOJO;

@Repository
public interface CentreAdministratorRepository extends JpaRepository<CentreAdministratorPOJO, Long>{

	CentreAdministratorPOJO findOneByEmail (String email);
	
}
