package com.ftn.dr_help.comon.automatically_reserving;

import java.util.Calendar;

import com.ftn.dr_help.model.pojo.RoomPOJO;

public class FreeRoomWithDate {

	private RoomPOJO freeRoom;
	private Calendar recomendedDate;
	
	public FreeRoomWithDate() {
		super();
	}
	
	public FreeRoomWithDate(RoomPOJO freeRoom, Calendar recomendedDate) {
		super();
		this.freeRoom = freeRoom;
		this.recomendedDate = recomendedDate;
	}

	public RoomPOJO getFreeRoom() {
		return freeRoom;
	}

	public void setFreeRoom(RoomPOJO freeRoom) {
		this.freeRoom = freeRoom;
	}

	public Calendar getRecomendedDate() {
		return recomendedDate;
	}

	public void setRecomendedDate(Calendar recomendedDate) {
		this.recomendedDate = recomendedDate;
	}
}
