package com.itechro.cas.model.security;

import java.io.Serializable;

public class LoginCredentials implements Serializable {

	private static final long serialVersionUID = 1561216215711790436L;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginCredentials{" +
				"username='" + username + '\'' +
				'}';
	}
}
