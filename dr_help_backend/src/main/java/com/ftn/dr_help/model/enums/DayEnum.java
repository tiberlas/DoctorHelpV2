package com.ftn.dr_help.model.enums;

public enum DayEnum {
	
	MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7), SUNDAY(1);
	
	private final int value;
    private DayEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
