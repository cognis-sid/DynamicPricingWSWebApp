package com.rclgroup.dolphin.web.email;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

import com.rclgroup.dolphin.web.model.EmailConfigMod;
import com.sun.mail.smtp.SMTPTransport;

public class EZLMail {

	private EmailConfigMod mailConfig;
	private String adminEmail;
	private Session session ;
	private MailSender mailSender;

	private JavaMailSender javaMailSender;

	private VelocityEngine velocityEngine;

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	

 
	public void setMailConfig(EmailConfigMod model) {
		this.mailConfig = model;
		Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.host", mailConfig.getHost());
        properties.put("mail.smtp.port",25);

        session = Session.getInstance(properties);
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	 

	public void sendPassword(final String mailTo,String toekn, final String name) throws Exception {
	        MimeMessage msg = new MimeMessage(session);	   
	        msg.setFrom(new InternetAddress(adminEmail));
	        System.out.println("mail.from : " + adminEmail);
	        String subject = " Your Request Quote Password is  "+toekn;
	        msg.addRecipients(Message.RecipientType.TO, System.getProperty("mail.to", mailTo));
	        msg.setSentDate(new Date());
	        msg.setSubject(subject);
	        msg.setHeader("Mime-Version", "1.0");
	        msg.setHeader("Content-Transfer-Encoding", "quoted-printable");
	        msg.setHeader("X-Mailer", "sendhtml");
	        msg.setHeader("Content-Type", "text/html");
	        msg.setHeader("Return-Path", "no-reply@rclgroup.com");
	        MimeBodyPart part1 = new MimeBodyPart();
	        
	        StringBuffer sb =new StringBuffer( GetMailProperticeServlet.mail.replace("token__id", toekn));	         
	        part1.setText(sb.toString()); // Writes a computed Content-Type Header
	        part1.setHeader("Content-Type","text/html; charset=us-ascii; format=flowed; delsp=yes"); 
	        MimeMultipart multipart = new MimeMultipart();
	        multipart.addBodyPart(part1);
	        
	        msg.setContent(multipart);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        msg.writeTo(outputStream);
	        if(msg.getAllRecipients().length>0) {
	        	System.out.println(msg.getAllRecipients()[0]);
	        }
	        SMTPTransport xp = (SMTPTransport) session.getTransport();
	        xp.connect();
	        xp.sendMessage(msg,msg.getAllRecipients());   
	    }

	 
}
