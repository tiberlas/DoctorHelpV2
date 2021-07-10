package com.ftn.dr_help.model.enums;

/* AVAILABLE_CONFLICT: Postoje samo predefined available appointmenti, admin moze da ih otkaze i blessuje request*/
/* APPROVED_CONFLICT: postoje neki approved u tom periodu, request se moze samo odbiti*/
/* ERROR: transakcija error*/
/* CAN_BLESS: :)*/

public enum LeaveRequestValidationEnum {
	AVAILABLE_CONFLICT, APPROVED_CONFLICT, ERROR, CAN_BLESS 
}

