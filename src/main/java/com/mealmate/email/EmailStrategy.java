package com.mealmate.email;

public interface EmailStrategy {
	void sendEmail(String userEmail, String token);
}
