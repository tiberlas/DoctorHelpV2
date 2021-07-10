package com.ftn.dr_help.dto;

public class RoomReservingInfoDTO {

	private Long idRoom;
	private String freeDate;
	private String roomName;
	private int roomNumber;
	
	public RoomReservingInfoDTO() {
		super();
	}
	
	public RoomReservingInfoDTO(Long idRoom, String freeDate, String roomName, int roomNumber) {
		super();
		this.idRoom = idRoom;
		this.freeDate = freeDate;
		this.roomName = roomName;
		this.roomNumber = roomNumber;
	}

	public Long getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(Long idRoom) {
		this.idRoom = idRoom;
	}

	public String getFreeDate() {
		return freeDate;
	}

	public void setFreeDate(String freeDate) {
		this.freeDate = freeDate;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
}
