package com.ftn.dr_help.dto.business_hours;

import java.util.List;

public class BusinessDayHoursDTO { /* JSON format that is expected on front-end: 
[ // specify an array instead
  {
    daysOfWeek: [ 1, 2, 3 ], // Monday, Tuesday, Wednesday
    startTime: '08:00', // 8am
    endTime: '18:00' // 6pm
  },
  {
    daysOfWeek: [ 4, 5 ], // Thursday, Friday
    startTime: '10:00', // 10am
    endTime: '16:00' // 4pm
  }
]*/

	private List<Integer> daysOfWeek;
	private String startTime;
	private String endTime;
	
	public BusinessDayHoursDTO() {
		
	}
	
	public List<Integer> getDaysOfWeek() {
		return daysOfWeek;
	}
	public void setDaysOfWeek(List<Integer> daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
