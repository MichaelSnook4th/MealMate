package com.mealmate.email;

public class EmailUtility {

    private static EmailUtility instance;
    
    private EmailUtility() {
    	
    }
    
    public static synchronized EmailUtility getInstance() {
    	if (instance == null) {
    		instance = new EmailUtility();
    	}
    	return instance;
    }
    
    public void sendEmail(EmailStrategy strategy, String userEmail, String token) {
    	strategy.sendEmail(userEmail, token);
    }
}
