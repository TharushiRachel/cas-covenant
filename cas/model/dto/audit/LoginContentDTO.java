package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class LoginContentDTO extends BaseContentDTO {

    @SerializedName("User Name")
    private String userName;

    @SerializedName("User ID")
    private Integer userID;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
