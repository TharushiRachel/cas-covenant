package com.itechro.cas.model.security;

import java.io.Serializable;

public class TokenDTO implements Serializable {

	private static final long serialVersionUID = -5786884512157378814L;

	private String token;

	private String refreshToken;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
