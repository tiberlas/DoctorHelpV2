package com.ftn.dr_help.config;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	/*
	 * It checks if the request has a valid JWT token. If it has a valid JWT Token, 
	 * then it sets the authentication in context to specify that the current user is authenticated.
	 * */
	
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public JwtRequestFilter(JwtTokenUtil tokenHelper, UserDetailsService userDetailsService) {
		this.jwtTokenUtil = tokenHelper;
		this.jwtUserDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String username;
		String authToken = jwtTokenUtil.getToken(request);

		if (authToken != null) {
			// uzmi username iz tokena
			username = jwtTokenUtil.getUsernameFromToken(authToken);
			
			if (username != null) {
				// uzmi user-a na osnovu username-a
				UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
				
				// proveri da li je prosledjeni token validan
				if (jwtTokenUtil.validateToken(authToken, userDetails)) {
					// kreiraj autentifikaciju
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					authentication.setAuthenticated(true);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
	
		
		// prosledi request dalje u sledeci filter
		chain.doFilter(request, response);
	}
}
