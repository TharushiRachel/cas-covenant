package com.itechro.cas.model.security;

import java.io.Serializable;

public class ApiKeys implements Serializable {

	private static final long serialVersionUID = 6260772368379705362L;

	private String publicKey;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
