package com.itechro.cas.model.dto.master;

import java.io.Serializable;

public class UserLoginResponse implements Serializable {

    private static final long serialVersionUID = -5035072224791080351L;

    private String accessToken;

    private String refreshToken;

    private UserDTO user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "user=" + user +
                '}';
    }
}
