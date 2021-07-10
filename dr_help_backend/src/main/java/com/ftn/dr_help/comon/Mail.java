package com.ftn.dr_help.comon;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ftn.dr_help.model.enums.RoleEnum;
import com.ftn.dr_help.model.pojo.AppointmentPOJO;
import com.ftn.dr_help.model.pojo.DoctorPOJO;
import com.ftn.dr_help.model.pojo.OperationPOJO;

@Service
public class Mail {

	@Autowired 
	private JavaMailSender javaMailSender;
	
	@Autowired
	private DateConverter dateConvertor;
	
	public void sendAccountInfoEmail(String sendTo, String password, String firstName, String lastName, RoleEnum role) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(sendTo);

        msg.setSubject("DrHelp account registration");
        String text = "Dear " + firstName + " " + lastName + ","+'\n';
        text += "An account has been linked to this email address. Your sign in credentials are: ";
        text += "\n\n\nEmail:\t" + sendTo;
        text += "\nPassword:\t" + password;
        
        if(role.equals(RoleEnum.CENTRE_ADMINISTRATOR)) {
        	text += "\nAppointed role:\tCentre administrator";
        } else if(role.equals(RoleEnum.CLINICAL_ADMINISTRATOR)) {
        	text += "\nAppointed role:\tClinic administrator";
        } else if(role.equals(RoleEnum.DOCTOR)) {
        	text += "\nAppointed role:\tDoctor";
        } else if(role.equals(RoleEnum.NURSE)) {
        	text += "\nAppointed role:\tNurse";
        }
        
        text += "\nPlease, change your password after logging in. \n" + "Forever helping, drHelp.";
        msg.setText(text);

        javaMailSender.send(msg);

    }
	
	
	//@Async
	public void sendAcceptEmail(String sendTo, String firstName, String lastName) throws MessagingException, IOException  {
		
		MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(sendTo);

        helper.setSubject("DrHelp account registration");
        String text = "Dear " + firstName + " " + lastName + "," + '\n';
        text += "your account request has been reviewed and accepted by our administrator staff. \n Please follow the link below to activate your account.";
        text += "http://localhost:3000/activate=" + sendTo + "\n\n\n" + "Forever helping, drHelp.";
        helper.setText(text);

       javaMailSender.send(msg);
	}

	//@Async
	public void sendDeclineEmail(String sendTo, String description, String firstName, String lastName) {

	    SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(sendTo);
	
	    msg.setSubject("DrHelp account registration");
	    String text = "Dear " + firstName + " " + lastName + "," + '\n';
	    text += "your account request has been reviewed. Unfortunately, it has been declined, with an administrator message attached:";
	    text += "\n\n\n" + description;
	    text += "\n\n\n" + "Forever helping, drHelp.";
	    msg.setText(text);
	
	    javaMailSender.send(msg);
	}
	
	public void sendAppointmentRequestEmail(String sendTo, String doctorName, String type, String date) {

	    SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(sendTo);
	
	    msg.setSubject("DrHelp request appointment");
	    String text = "Dr " + doctorName + " request an appointment for " + type;
	    text += " at " + date;
	    text += "\n\n\n" + "Forever helping, drHelp.";
	    msg.setText(text);
	
	    javaMailSender.send(msg);
	}
	
	public void sendOperationRequestEmail(String sendTo, String requestingDoctorName, String type, String date) {

	    SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(sendTo);
	
	    msg.setSubject("DrHelp request operation");
	    String text = "Dr " + requestingDoctorName + " request an operation for " + type;
	    text += " at " + date;
	    
	    text += "\n\n\n" + "Forever helping, drHelp.";
	    msg.setText(text);
	
	    javaMailSender.send(msg);
	}
	

	@Async
	public void sendDeclineLeaveRequestEmail(String sendTo, String description, String firstName, String lastName, String leaveType, String fromDate, String toDate) {

	    SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(sendTo);
	
	    msg.setSubject("DrHelp leave request");
	    String text = "Dear " + firstName + " " + lastName + "," + '\n';
	    text += "Your " + leaveType + " leave request from " + fromDate + " to " +toDate + " has been declined with an administrator message attached:";
	    text += "\n\n\n" + description;
	    
	    text += "\n\n\n" + "Forever helping, drHelp.";
	    msg.setText(text);
	    javaMailSender.send(msg);
	    
	}
	    
	    

	public void sendAppointmentBlessedEmail(AppointmentPOJO appointment) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(appointment.getPatient().getEmail());
	
	    msg.setSubject("DrHelp requesting appointment");
	    String text = "Dear " + appointment.getPatient().getFirstName() +" "+appointment.getPatient().getLastName()+ 
	    		" your appointemnt for " +appointment.getProcedureType().getName()+" has been scheduled for "+
	    		dateConvertor.dateForFrontEndString(appointment.getDate())+", in room "+
	    		appointment.getRoom().getName()+" number "+appointment.getRoom().getNumber()+
	    		". Dr "+appointment.getDoctor().getFirstName()+
	    		" "+appointment.getDoctor().getLastName()+" will examin you.";
	    text += "\n";
	    text += "Please log on to the website, check your pending appointments, and confirm, before comming. ";
	    //NIKOLA OVDE TI DODAS LINK KA ODOBRAVANJU PREGLEDA
//	    text += "http://localhost:3000/activate=" + sendTo;
	    
	    
	    text += "\n\n\n" + "Forever helping, drHelp.";
	    msg.setText(text);
	
	    javaMailSender.send(msg);
	    System.out.println("Sent leave request declination mail to nurse.");
	}
	
	
	@Async
	public void sendAcceptLeaveRequestEmail(String sendTo, String firstName, String lastName, String leaveType, String fromDate, String toDate) {

	    SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(sendTo);
	
	    msg.setSubject("DrHelp leave request");
	    String text = "Dear " + firstName + " " + lastName + "," + '\n';
	    text += "Your " + leaveType + " leave request from " + fromDate + " to " +toDate + " has been accepted!";
	    text += "\nWe wish you the best during your absence, you deserve it.";
	    msg.setText(text);
	    
	    text += "\n\n\n" + "Forever helping, drHelp.";
	    javaMailSender.send(msg);

	}
	
	public void sendAppointmentApprovedToPatientEmail(AppointmentPOJO appointment) {
			
			SimpleMailMessage msg = new SimpleMailMessage();
		    msg.setTo(appointment.getPatient().getEmail());
		
		    msg.setSubject("DrHelp appointment check");
		    String text = "Dear " + appointment.getPatient().getFirstName() +" "+appointment.getPatient().getLastName()+ 
		    		" your appointemnt for " +appointment.getProcedureType().getName()+" has been schedule for "+
		    		dateConvertor.dateForFrontEndString(appointment.getDate())+", in room "+
		    		appointment.getRoom().getName()+" number "+appointment.getRoom().getNumber()+
		    		". Dr "+appointment.getDoctor().getFirstName()+
		    		" "+appointment.getDoctor().getLastName()+" will examin you.";
		    
		    text += "\n\n\n" + "Forever helping, drHelp.";
		    msg.setText(text);
		
		    javaMailSender.send(msg);
	}
	
	public void sendAppointmentApprovedToDoctorEmail(AppointmentPOJO appointment) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(appointment.getDoctor().getEmail());
	
	    msg.setSubject("DrHelp appointment check");
	    String text = "Dear dr " + appointment.getDoctor().getFirstName() +" "+appointment.getDoctor().getLastName()+ 
	    		" your appointemnt for patient "+appointment.getPatient().getFirstName()+" "+appointment.getPatient().getLastName()+
	    		" has been scheduled for "+dateConvertor.dateForFrontEndString(appointment.getDate())+", in room "+
			    appointment.getRoom().getName()+" number "+appointment.getRoom().getNumber();
	    
	    text += "\n\n\n" + "Forever helping, drHelp.";
	    msg.setText(text);
	
	    javaMailSender.send(msg);
	}

	public void sendAppointmentApprovedToNurseEmail(AppointmentPOJO appointment) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(appointment.getNurse().getEmail());
	
	    msg.setSubject("DrHelp appointment reserved");
	    String text = "Dear " + appointment.getNurse().getFirstName() +" "+appointment.getNurse().getLastName()+ 
	    		" you have an appointemnt on "+
	    		dateConvertor.dateForFrontEndString(appointment.getDate())+", in room "+
			    appointment.getRoom().getName()+" number "+appointment.getRoom().getNumber();

	    text += "\n\n\n" + "Forever helping, drHelp.";
	    msg.setText(text);
	
	    javaMailSender.send(msg);

	    System.out.println("Sent leave request declination mail to nurse.");
	}
	
	public void sendOperationApprovedToDoctorsEmail(DoctorPOJO doctor, OperationPOJO operation) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
	    msg.setTo(doctor.getEmail());
	
		msg.setSubject("DrHelp operation scheduled");
		  String text = "Dear " + doctor.getFirstName() +" "+doctor.getLastName()+ 
		    		"\n "+ operation.getOperationType().getName() + " is scheduled at " + 
		    		dateConvertor.dateForFrontEndString(operation.getDate())+", in";
	  	if(operation.getRoom() != null) {
	    	text += " room " + operation.getRoom().getName()+" number "+operation.getRoom().getNumber();
	    } else {
	    	text += "undefined room";
	    }
	  
	    
	    text += "\n\n\n" + "Forever helping, drHelp.";
	    msg.setText(text);
	
	    javaMailSender.send(msg);
	}
	
	public void sendOperationApprovedToPatientEmail(OperationPOJO operation) {
			
			SimpleMailMessage msg = new SimpleMailMessage();
		    msg.setTo(operation.getPatient().getEmail());
		    String text = "Dear " + operation.getPatient().getFirstName() +" "+operation.getPatient().getLastName()+"."+ 
		    		"\n\nYour " +operation.getOperationType().getName() + " has been scheduled.";
		    
		    if(operation.getRoom() != null) {
		    	text +=  "\n\nRoom: "+
			    		operation.getRoom().getName()+"\nNumber: "+operation.getRoom().getNumber();
		    } else {
		    	text += "in an undefined room";
		    }
		    msg.setSubject("DrHelp operation scheduled");
		    text += "Operation held at: ";
		    text += dateConvertor.dateForFrontEndString(operation.getDate())+"\n" +
		    		"By: dr. "+operation.getFirstDoctor().getFirstName()+" "+operation.getFirstDoctor().getLastName()+
		    		", dr. "+operation.getSecondDoctor().getFirstName()+" "+operation.getSecondDoctor().getLastName()+
		    		" and dr. "+operation.getThirdDoctor().getFirstName()+" "+operation.getThirdDoctor().getLastName()+".";
		    
		    text += "\n\n\n" + "Forever helping, drHelp.";
		    msg.setText(text);
		
		    javaMailSender.send(msg);
	}
	
	
	
}
