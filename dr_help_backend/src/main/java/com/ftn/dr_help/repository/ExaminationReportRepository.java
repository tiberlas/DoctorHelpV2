package com.ftn.dr_help.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.dr_help.model.pojo.ExaminationReportPOJO;

public interface ExaminationReportRepository extends JpaRepository<ExaminationReportPOJO, Long>{

	List<ExaminationReportPOJO> findAllByHealthRecordId (Long healthRecordId);
	
}
