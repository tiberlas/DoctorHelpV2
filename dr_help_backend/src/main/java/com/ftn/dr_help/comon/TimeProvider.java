package com.ftn.dr_help.comon;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimeProvider implements Serializable {

	/*
	 * just gives the current date; it is used for JWT to set the time of creating JWT
	 * */
	
    private static final long serialVersionUID = -3301695478208950415L;

    public Date now() {
        return new Date();
    }

}
