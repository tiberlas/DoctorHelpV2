package com.ftn.dr_help.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.dr_help.comon.CurrentUser;
import com.ftn.dr_help.dto.ProcedureIdAndDateDTO;
import com.ftn.dr_help.dto.RoomCalendarDTO;
import com.ftn.dr_help.dto.RoomDTO;
import com.ftn.dr_help.dto.RoomReservingInfoDTO;
import com.ftn.dr_help.dto.RoomSearchDTO;
import com.ftn.dr_help.service.RoomService;

@RestController
@RequestMapping(value = "api/rooms")
public class RoomController {
	
	@Autowired
	private RoomService service;
	
	@Autowired
	private CurrentUser currentUser;
	
	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<RoomDTO>> getAllRooms() {
		String email = currentUser.getEmail();
		List<RoomDTO> finded = service.findAll(email);
		
		if(finded == null || finded.isEmpty())
			return new ResponseEntity<List<RoomDTO>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<RoomDTO>>(finded,  HttpStatus.OK);
	}
	
	@GetMapping(value = "/one/room={room_id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<RoomDTO> getOneRooms(@PathVariable("room_id") Long room_id) {
		
		String email = currentUser.getEmail();
		RoomDTO finded = service.findOne(room_id, email);
		
		if(finded == null)
			return new ResponseEntity<RoomDTO>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<RoomDTO>(finded,  HttpStatus.OK);
	}
	
	@PostMapping(value= "/new+room", consumes = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<RoomDTO> saveRoom(@RequestBody RoomDTO newRoom) {
		String email = currentUser.getEmail();
		
		RoomDTO saved = service.save(newRoom, email);
		
		if(saved == null) {
			return new ResponseEntity<RoomDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<RoomDTO>(saved, HttpStatus.OK);
	}

	@DeleteMapping(value="/delete/id={id}")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<String> deleteRoom(@PathVariable("id") Long id) {
		String email = currentUser.getEmail();
		boolean retVal = service.delete(id, email);
		
		if(retVal) {
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/change", consumes = "application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<RoomDTO> changeRoom(@RequestBody RoomDTO room) {
		String email = currentUser.getEmail();
		RoomDTO ret = service.change(room, email);
		
		if(ret == null) {
			return new ResponseEntity<RoomDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		return new ResponseEntity<RoomDTO>(ret, HttpStatus.OK);
	}
	
	@GetMapping(value="room={id}/schedule", produces="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<RoomCalendarDTO>> getSchedule(@PathVariable("id") Long roomId) {
		
		try {
			List<RoomCalendarDTO> ret = service.getSchedule(roomId);
		
			return new ResponseEntity<>(ret, HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);			
		}			
	}
	
	@PostMapping(value="search", consumes="application/json", produces="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<RoomDTO>> search(@RequestBody RoomSearchDTO searchingParameters) {
		String email = currentUser.getEmail();
		List<RoomDTO> retVal = service.search(searchingParameters, email);
		
		if(retVal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(retVal, HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/all", produces="application/json")
	@PreAuthorize("hasAuthority('CLINICAL_ADMINISTRATOR')")
	public ResponseEntity<List<RoomReservingInfoDTO>> getAllRoomWithType(@RequestBody ProcedureIdAndDateDTO request) {
		String email = currentUser.getEmail();
		
		List<RoomReservingInfoDTO> rooms = service.getAllWithType(email, request);
		
		if(rooms == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(rooms, HttpStatus.OK);
		}
	}
	
}
