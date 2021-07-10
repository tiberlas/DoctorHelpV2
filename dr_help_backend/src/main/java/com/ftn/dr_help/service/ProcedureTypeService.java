package com.ftn.dr_help.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.dr_help.dto.ProcedureTypeDTO;
import com.ftn.dr_help.dto.ProcedureTypeFilterDTO;
import com.ftn.dr_help.dto.ProcedureTypeInfoDTO;
import com.ftn.dr_help.model.enums.FilterOperationEnum;
import com.ftn.dr_help.model.pojo.ClinicAdministratorPOJO;
import com.ftn.dr_help.model.pojo.ClinicPOJO;
import com.ftn.dr_help.model.pojo.ProceduresTypePOJO;
import com.ftn.dr_help.repository.ClinicAdministratorRepository;
import com.ftn.dr_help.repository.DoctorRepository;
import com.ftn.dr_help.repository.ProcedureTypeRepository;

@Service
public class ProcedureTypeService {

	@Autowired
	private ProcedureTypeRepository procedureTypeRepository;
	
	@Autowired
	private ClinicAdministratorRepository adminRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	
	public List<String> getProcedureTypes () {
		return procedureTypeRepository.getProcedureTypes();
	}
	
	public Double getPrice (Long clinicId, String procedureName) {
		//System.out.println("PROCEDURE TYPE, get price, clinic id: " + clinicId + "; Procedure name: " + procedureName);
		Double retVal = procedureTypeRepository.getPrice(clinicId, procedureName);
		//System.out.println("Procedure type service, evo sta sam iskopao iz baze: " + retVal);
		return retVal;
	}
	
    public List<ProcedureTypeDTO> getAll(String email) {
    	ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
    	if( admin == null) {
    		return null;
    	}
    	
    	ClinicPOJO clinic = admin.getClinic();
    	if(clinic == null) {
    		return null;
    	}
    	
    	List<ProceduresTypePOJO> finded = clinic.getProcedureTypesList();
    	List<ProcedureTypeDTO> ret = new ArrayList<>();    	

        for(ProceduresTypePOJO pojo : finded) {
        	if(pojo.isDeleted() == false) {
        		ret.add(new ProcedureTypeDTO(pojo));
        	}
        }

        return ret;
    }

    @Transactional(readOnly = true)
    public ProcedureTypeDTO getOne(Long id) {
    	if( id == null) {
    		return null;
    	}
    	
        ProceduresTypePOJO finded = procedureTypeRepository.findById(id).orElse(null);

        ProcedureTypeDTO ret = new ProcedureTypeDTO(finded);
        return ret;
    }

    public ProcedureTypeDTO save(ProcedureTypeDTO newProcedure, String email) {
		if(newProcedure == null) {
			return null;
		}
		
		List<ProceduresTypePOJO> list = procedureTypeRepository.findAllByName(newProcedure.getName());
		
		ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
		if(admin == null) {
			return null;
		}
		
		for (ProceduresTypePOJO p : list) {
			if (p.getClinic().getId() == admin.getClinic().getId()) {
				return null;
			}
		}
		
		ClinicPOJO clinic = admin.getClinic();
        ProceduresTypePOJO procedure = new ProceduresTypePOJO();
        procedure.setClinic(clinic);
        procedure.setPrice(newProcedure.getPrice());
        procedure.setName(newProcedure.getName());
        procedure.setDuration(newProcedure.getDuration());
        procedure.setOperation(newProcedure.isOperation());
        procedure.setDeleted(false);
		
        procedureTypeRepository.save(procedure);
		
		return new ProcedureTypeDTO(procedure);
    }
    
    public boolean delete(Long id, String email) {
		
		if(id == null) {
			return false;
		}
		
		ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
		if(admin == null) {
			return false;
		}
		
		ClinicPOJO clinic = admin.getClinic();
		List<ProceduresTypePOJO> findedList = clinic.getProcedureTypesList();
		if(findedList == null || findedList.isEmpty()) {
			return false;
		}
		
		ProceduresTypePOJO finded = null;
		for(ProceduresTypePOJO pojo : findedList) {
			if(pojo.getId().equals(id)) {
				finded = pojo;
				break;
			}
		}
		
		if(finded == null) {
			return false;
		}
		
		//ako postoji appointment sa ovim tipom ne moze obrisati
		if(finded.getAppointment().isEmpty()) {
			finded.setDeleted(true);
			procedureTypeRepository.save(finded);
			
			return true;
		}
		
		return false;
	}
	
	public ProcedureTypeDTO change(ProcedureTypeDTO procedure, String email) {
		
		if(procedure == null || procedure.getId() == null) {
			return null;
		}
		
		ClinicAdministratorPOJO admin = adminRepository.findOneByEmail(email);
		if(admin == null) {
			return null;
		}
		
		ClinicPOJO clinic = admin.getClinic();
		List<ProceduresTypePOJO> findedList = clinic.getProcedureTypesList();
		if(findedList == null || findedList.isEmpty()) {
			return null;
		}
		
		ProceduresTypePOJO finded = null;
		for(ProceduresTypePOJO pojo : findedList) {
			if(pojo.getId().equals(procedure.getId())) {
				finded = pojo;
				break;
			}
		}
		
		if(finded == null) {
			return null;
		}
		
		if(!finded.getName().equals(procedure.getName())) {
			ProceduresTypePOJO exist = procedureTypeRepository.findOneByName(procedure.getName()).orElse(null);
			if(exist != null) {
				return null;
			}
		}
		
		//ako postoji appointment sa ovim tipom ne moze se menjati
		if(!finded.getAppointment().isEmpty()) {
			return null;
		}
		
		finded.setPrice(procedure.getPrice());
		finded.setName(procedure.getName());
		finded.setDuration(procedure.getDuration());
		finded.setOperation(procedure.isOperation());
		finded.setDeleted(false);
		
		procedureTypeRepository.save(finded);
		
		return new ProcedureTypeDTO(finded);
	}
	
	public List<ProcedureTypeDTO> filter (ProcedureTypeFilterDTO filter, String email) {
		
		List<ProcedureTypeDTO> finded = getAll(email);
		if(filter.getString().isEmpty()) {
			if(filter.getOperation() == FilterOperationEnum.NOT_DEFINED) {
				return finded;				
			} else if(filter.getOperation() == FilterOperationEnum.OPERATION) {
				return operationsOrNot(finded, true);
			} else {
				return operationsOrNot(finded, false);
			}
		}
		
		List<ProcedureTypeDTO> ret = new ArrayList<ProcedureTypeDTO>();
		String search = "";
		for(ProcedureTypeDTO item : finded) {
			search = item.getName().toLowerCase() + String.valueOf(item.getPrice()) + String.valueOf(item.getDuration());
			System.out.println(search);
			if(search.contains(filter.getString().toLowerCase())) {
				ret.add(item);			
			}
		}
		
		if(ret.isEmpty()) {
			return ret;
		}
		
		if(filter.getOperation() == FilterOperationEnum.NOT_DEFINED) {
			return ret;				
		} else if(filter.getOperation() == FilterOperationEnum.OPERATION) {
			return operationsOrNot(ret, true);
		} else {
			return operationsOrNot(ret, false);
		}
	}
	
	private List<ProcedureTypeDTO> operationsOrNot(List<ProcedureTypeDTO> procedures, boolean isOperation) {
		List<ProcedureTypeDTO> ret = new ArrayList<>();
		
		for(ProcedureTypeDTO procedure : procedures) {
			if(procedure.isOperation() == isOperation) {
				ret.add(procedure);
			}
		}
		
		return ret;
	}
	
	public List<ProcedureTypeInfoDTO> allOperation(String email) {
		try {
			Long clinicId = doctorRepository.findOneByEmail(email).getClinic().getId();

			List<ProceduresTypePOJO> findedOperations = procedureTypeRepository.findAllOperations(clinicId);
			List<ProcedureTypeInfoDTO> operations = new ArrayList<>();

			if(findedOperations.isEmpty()) {
				return operations;
			}
			
			for(ProceduresTypePOJO procedure : findedOperations) {
				operations.add(new ProcedureTypeInfoDTO(
							procedure.getId(),
							procedure.getName(),
							procedure.getPrice()
						));
			}
			
			return operations;
		} catch(Exception e) {
			return null;
		}
	}
	
	public List<ProcedureTypeDTO> getAllNotOpeations(String email) {
    	try {
    		List<ProceduresTypePOJO> finded = procedureTypeRepository.getAllNotOperations(email);
        	List<ProcedureTypeDTO> ret = new ArrayList<>();    	

            for(ProceduresTypePOJO pojo : finded) {
            	ret.add(new ProcedureTypeDTO(pojo));
            }

            return ret;
    		
    	} catch(Exception e) {
    		return null;
    	}
    }
	
}
