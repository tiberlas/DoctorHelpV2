package com.ftn.dr_help.aspect;

import java.util.Calendar;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.dr_help.repository.AppointmentRepository;

@Component
@Aspect
public class AppointmentCleanupAspect {
	
	@Autowired 
	private AppointmentRepository appointmentRepository;

	
	//aspekt se poziva pre findDoctorAppointments i cisti appointmente koji se nisu izvrsili u proslosti
	@Before("execution(* com.ftn.dr_help.service.AppointmentService.findDoctorAppointments(..)) && args(doctor_id,..)")
	public void findDoctorAppointmentsAdviceBefore(JoinPoint joinPoint, Long doctor_id) throws Throwable {
		System.out.println("Objekat koji se prosledjuje metodi: " + doctor_id);
		
		Calendar now = Calendar.getInstance();
		
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.add(Calendar.DAY_OF_MONTH, -1);
		
		
		System.out.println("NOW GET TIME"+ now.getTime());
		appointmentRepository.deleteAppointmentsInThePast(now.getTime());
	}
}
