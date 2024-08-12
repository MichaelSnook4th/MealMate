package com.mealmate.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VerificationEmailStrategy implements EmailStrategy {

	private static final String HOST = "smtp.office365.com";
    private static final int PORT = 587;
    private static final String EMAIL = "mealmate09@outlook.com";
    private static final String PASSWORD = "09MealMatepassword";
    
	@Override
	public void sendEmail(String userEmail, String token) {
		Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Email Verification");
            message.setText("Please verify your email using this link: "
                    + "http://localhost:8080/Meal_Mate/VerifyServlet?token=" + token);

            Transport.send(message);

            System.out.println("Verification email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }		
	}
}
