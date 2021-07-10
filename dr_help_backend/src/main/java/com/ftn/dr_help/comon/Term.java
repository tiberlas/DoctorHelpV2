package com.ftn.dr_help.comon;

import java.util.Calendar;

public class Term {

	public Term() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Term(Calendar time, boolean isTaken) {
		super();
		this.time = time;
		this.isTaken = isTaken;
	}
	private Calendar time;
	private boolean isTaken;
	public Calendar getTime() {
		return time;
	}
	public void setTime(Calendar time) {
		this.time = time;
	}
	public boolean isTaken() {
		return isTaken;
	}
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
}