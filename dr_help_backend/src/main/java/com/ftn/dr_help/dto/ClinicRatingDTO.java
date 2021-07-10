package com.ftn.dr_help.dto;

public class ClinicRatingDTO {

	public ClinicRatingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClinicRatingDTO(boolean haveInteracted, int myRating) {
		super();
		this.haveInteracted = haveInteracted;
		this.myRating = myRating;
	}
	private boolean haveInteracted = false;
	private int myRating = 0;
	public boolean isHaveInteracted() {
		return haveInteracted;
	}
	public void setHaveInteracted(boolean haveInteracted) {
		this.haveInteracted = haveInteracted;
	}
	public int getMyRating() {
		return myRating;
	}
	public void setMyRating(int myRating) {
		this.myRating = myRating;
	}
	
}
