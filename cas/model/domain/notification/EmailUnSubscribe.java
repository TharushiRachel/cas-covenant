package com.itechro.cas.model.domain.notification;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_EMAIL_UN_SUBSCRIBE")
public class EmailUnSubscribe extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_EMAIL_UN_SUBSCRIBE")
    @SequenceGenerator(name = "SEQ_T_EMAIL_UN_SUBSCRIBE", sequenceName = "SEQ_T_EMAIL_UN_SUBSCRIBE", allocationSize = 1)
    @Column(name = "EMAIL_UN_SUBSCRIBE_ID")
    private Integer emailUnSubscribeID;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getEmailUnSubscribeID() {
        return emailUnSubscribeID;
    }

    public void setEmailUnSubscribeID(Integer emailUnSubscribeID) {
        this.emailUnSubscribeID = emailUnSubscribeID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

}
