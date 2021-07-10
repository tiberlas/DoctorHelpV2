package com.ftn.dr_help.comon.automatically_reserving;

import java.util.Calendar;

public interface CheckRoomsInterface {

	public FreeRoomWithDate findFirstFreeRoom(Calendar requestingDate, Long clinicId, Long procedureTypeId);
}
