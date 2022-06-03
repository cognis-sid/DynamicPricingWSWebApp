package com.rclgroup.dolphin.web.email;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.rclgroup.dolphin.web.model.EmailConfigMod;

public class EZLMail2 {

	private static Session session ;
	public static void setMailConfig(EmailConfigMod model) {
		
		Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.host", model.getHost());
        properties.put("mail.smtp.port",25);

        session = Session.getInstance(properties);
	}

	public  static void sendPassword( String mailTo,String toekn,String name) throws Exception {
			//mailTo="sushil@cognissolutions.com";
	        MimeMessage msg = new MimeMessage(session);	   
	       //  msg.setFrom(new InternetAddress("eservice-uat@rclgroup.com"));// this for STG
	        msg.setFrom(new InternetAddress("eservice@rclgroup.com"));
		       
	        String subject = " Your Request Quote Password is  "+toekn;
	        msg.addRecipients(Message.RecipientType.TO, System.getProperty("mail.to", mailTo));
	        msg.setSentDate(new Date());
	        msg.setSubject(subject);
	        msg.setHeader("Mime-Version", "1.0");
	        msg.setHeader("Content-Transfer-Encoding", "quoted-printable");
	        msg.setHeader("X-Mailer", "sendhtml");
	        msg.setHeader("Content-Type", "text/html");
	        msg.setHeader("Return-Path", "bounce-eservice@rclgroup.com");
	        MimeBodyPart part1 = new MimeBodyPart();	        
	        String sb ="Your Request Quote Password is : "+toekn;
	        part1.setText(sb.toString()); // Writes a computed Content-Type Header
	        
	        MimeMultipart multipart = new MimeMultipart();
	        multipart.addBodyPart(part1);
	        
	        msg.setContent(multipart);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        msg.writeTo(outputStream);
	        RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

	        /*// Credentials
	        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
	        		.withRegion(Regions.AP_SOUTHEAST_1)
	                .withCredentials(credentialsProvider).build();*/
	        
	        // Credentials
	        AmazonSimpleEmailService client = 
	                AmazonSimpleEmailServiceClientBuilder.standard()
	                  .withRegion(Regions.AP_SOUTHEAST_1)                  
	                  .build();
	        // Send Mail
	        SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
	        client.sendRawEmail(rawEmailRequest);    
	    }

	
	static  AWSCredentialsProvider credentialsProvider = new AWSCredentialsProvider() {
	    @Override
	    public void refresh() {}
	        @Override
	        public AWSCredentials getCredentials() {
	        return new AWSCredentials() {
	            @Override
	            public String getAWSSecretKey() {
	                return "vQl4LqLT6Fz8y3leKnKwaX4kkfaSbka30KElFNlT";
	            }
	            @Override
	            public String getAWSAccessKeyId() {
	                return "AKIA3ZJZSQOMHOHX3WHV";
	            }
	        };
	    }
	};
	
	 
}
