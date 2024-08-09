package com.mealmate;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtility {

    private static final String HOST = "smtp.office365.com";
    private static final int PORT = 587;
    private static final String EMAIL = "mealmate09@outlook.com";
    private static final String PASSWORD = "09MealMatepassword";

    public static void sendVerificationEmail(String userEmail, String token) {
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
                    + "http://localhost:8080/MealMate/verify?token=" + token);

            Transport.send(message);

            System.out.println("Verification email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendPasswordRecoveryEmail(String userEmail, String token) {
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
            message.setSubject("Password Recovery");
            message.setText("Please click on the link below to reset your password: "
                    + "http://localhost:8080/MealMate/PasswordRecoveryServlet?token=" + token);

            Transport.send(message);

            System.out.println("Password recovery email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
