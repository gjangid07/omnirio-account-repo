package com.omnirio.Account.model;

public class AuthenticationResponse {

	private String jwtToken;

	public AuthenticationResponse(String token) {
		jwtToken = token;
	}

	public String getJwtToken() {
		return jwtToken;
	}

}
