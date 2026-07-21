package com.itechro.cas.model.dto.master;

import java.io.Serializable;

public class UserLoginFailedResponse implements Serializable {

    private static final long serialVersionUID = -4006189148132103241L;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserLoginFailedResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
