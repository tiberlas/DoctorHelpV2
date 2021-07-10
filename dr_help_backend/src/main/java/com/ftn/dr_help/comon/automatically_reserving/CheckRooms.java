package com.ftn.dr_help.comon.automatically_reserving;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.dr_help.model.pojo.RoomPOJO;
import com.ftn.dr_help.service.RoomService;

@Component
public class CheckRooms implements CheckRoomsInterface{

	@Autowired
	private RoomService roomService;
	
	@Override
	public FreeRoomWithDate findFirstFreeRoom(Calendar requestingDate, Long clinicId, Long procedureTypeId) {
		
		List<RoomPOJO> rooms = roomService.getAllRoomFromClinicWithProcedure(clinicId, procedureTypeId);
		
		Calendar freeDate = Calendar.getInstance();
		freeDate.add(Calendar.YEAR, 1);
		RoomPOJO freeRoom = null;
		for(RoomPOJO room : rooms) {
			Calendar findedFreeDate = roomService.findFirstFreeScheduleFromDateInRawformat(room, requestingDate);
			if(findedFreeDate.equals(requestingDate)) {
				return new FreeRoomWithDate(room, null);
			} else if(findedFreeDate.before(freeDate)) {
				freeDate = findedFreeDate;
				freeRoom = room;
			}
		}
		
		return new FreeRoomWithDate(freeRoom, freeDate);
	}

}
