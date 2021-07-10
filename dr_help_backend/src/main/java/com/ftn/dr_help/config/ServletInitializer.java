package com.ftn.dr_help.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.ftn.dr_help.DrHelpApplication;

public class ServletInitializer extends SpringBootServletInitializer {

	/*
	 * Note that a WebApplicationInitializer is only needed if you are building a war file and deploying it.
	 * If you prefer to run an embedded web server then you won't need this at all.
	 * */
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DrHelpApplication.class);
	}

}
