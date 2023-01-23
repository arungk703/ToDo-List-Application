package com.springbootpractice.ToDo.List.Login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	
	public boolean authenticate(String userName, String password) {
		
		boolean isValidUserName= userName.equals("Arun");
		boolean isValidPassword = password.equals("12345");
		
		return isValidUserName && isValidPassword;
	}

}
