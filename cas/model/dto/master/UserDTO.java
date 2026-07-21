package com.itechro.cas.model.dto.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.master.Role;
import com.itechro.cas.model.domain.master.User;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -2812615800057620985L;

    private Integer userID;

    private UserDTO supervisor;

    private String userName;

    private DomainConstants.Title title;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private String dobStr;

    private DomainConstants.Gender gender;

    private String address;

    private String contactNo;

    private String imagePath;

    private String branchCode;

    private String divCode;

    private String solID;

    private String displayName;

    private String adUserID;

    private String upmGroupCode;

    private String upmID;

    private DomainConstants.UserType userType;

    private Integer userRefID;

    private AppsConstants.Status status;

    private List<Integer> roles;

    private List<Integer> addedRoles;

    private List<Integer> removedRoles;

    private SortedSet<String> privileges;

    private Boolean isAssistantUser;

    private String designation;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user, true);
    }

    public UserDTO(UpmDetailResponse upmUser) {
        this.userID = Integer.parseInt(upmUser.getUserID());
        this.adUserID = upmUser.getAdUserID();
        this.divCode = upmUser.getDivCode();
        this.solID = upmUser.getSolID();
        this.upmID = upmUser.getUserID();
        this.upmGroupCode = upmUser.getApplicationSecurityClass();
        this.branchCode = upmUser.getDivCode();
        this.email = upmUser.getEmail();
        this.firstName = upmUser.getFirstName();
        this.lastName = upmUser.getLastName();
        if (StringUtils.isNotEmpty(this.firstName) && StringUtils.isNotEmpty(this.lastName)) {
            this.displayName = upmUser.getFirstName().concat(" ").concat(upmUser.getLastName());
        }
    }

    public UserDTO(User user, boolean loadRoles) {
        this.userID = user.getUserID();
        this.userName = user.getUserName();
        this.title = user.getTitle();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        if (user.getDateOfBirth() != null) {
            this.dobStr = CalendarUtil.getDefaultFormattedDateOnly(user.getDateOfBirth());
        }
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.contactNo = user.getContactNo();
        this.imagePath = user.getImagePath();
        this.userType = user.getUserType();
        this.userRefID = user.getUserRefID();
        this.status = user.getStatus();

        if (loadRoles) {
            for (Role role : user.getRoles()) {
                this.getRoles().add(role.getRoleID());
            }
        }
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public UserDTO getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(UserDTO supervisor) {
        this.supervisor = supervisor;
    }

    public String getUserName() {
        return userName!= null ? userName.toLowerCase() : null;
    }

    public void setUserName(String userName) {
        this.userName = userName!= null ? userName.toLowerCase() : null;
    }

    public DomainConstants.Title getTitle() {
        return title;
    }

    public void setTitle(DomainConstants.Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDobStr() {
        return dobStr;
    }

    public void setDobStr(String dobStr) {
        this.dobStr = dobStr;
    }

    public DomainConstants.Gender getGender() {
        return gender;
    }

    public void setGender(DomainConstants.Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDisplayName() {
        return displayName != null ? displayName.toLowerCase() : null;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName != null ? displayName.toLowerCase() : null;
    }

    public String getAdUserID() {
        return adUserID;
    }

    public void setAdUserID(String adUserID) {
        this.adUserID = adUserID;
    }

    public String getUpmGroupCode() {
        return upmGroupCode;
    }

    public void setUpmGroupCode(String upmGroupCode) {
        this.upmGroupCode = upmGroupCode;
    }

    public String getUpmID() {
        return upmID;
    }

    public void setUpmID(String upmID) {
        this.upmID = upmID;
    }

    public DomainConstants.UserType getUserType() {
        return userType;
    }

    public void setUserType(DomainConstants.UserType userType) {
        this.userType = userType;
    }

    public Integer getUserRefID() {
        return userRefID;
    }

    public void setUserRefID(Integer userRefID) {
        this.userRefID = userRefID;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public SortedSet<String> getPrivileges() {
        if (privileges == null) {
            privileges = new TreeSet<>();
        }
        return privileges;
    }

    public void setPrivileges(SortedSet<String> privileges) {
        this.privileges = privileges;
    }

    public void addPrivilege(String privilegeCode) {
        if (privileges == null) {
            privileges = new TreeSet<>();
        }
        privileges.add(privilegeCode);
    }

    public List<Integer> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public List<Integer> getAddedRoles() {
        if (addedRoles == null) {
            addedRoles = new ArrayList<>();
        }
        return addedRoles;
    }

    public void setAddedRoles(List<Integer> addedRoles) {
        this.addedRoles = addedRoles;
    }

    public List<Integer> getRemovedRoles() {
        if (removedRoles == null) {
            removedRoles = new ArrayList<>();
        }
        return removedRoles;
    }

    public void setRemovedRoles(List<Integer> removedRoles) {
        this.removedRoles = removedRoles;
    }

    public String getFullName() {
        if (this.title != null && StringUtils.isNotEmpty(this.firstName) && StringUtils.isNotEmpty(this.lastName)) {
            return this.title.getDescription() + " " + this.firstName + " " + this.lastName;
        }
        return null;
    }

    public String getCombinedName() {
        if (StringUtils.isNotEmpty(this.firstName) && StringUtils.isNotEmpty(this.lastName)) {
            return this.firstName + " " + this.lastName;
        }
        return null;
    }

    public boolean isAgent() {
        return this.getUserType() != null
                && this.getUserType() == DomainConstants.UserType.AGENT;
    }

    public boolean isAdmin() {
        return this.getUserType() != null
                && this.getUserType() == DomainConstants.UserType.ADMIN;
    }

    public String getSolID() {
        return solID;
    }

    public void setSolID(String solID) {
        this.solID = solID;
    }

    public String getDivCode() {
        return divCode;
    }

    public void setDivCode(String divCode) {
        this.divCode = divCode;
    }

    public Boolean getIsAssistantUser() {
        return isAssistantUser;
    }

    public void setIsAssistantUser(Boolean assistantUser) {
        isAssistantUser = assistantUser;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", title=" + title +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dobStr='" + dobStr + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", divCode='" + divCode + '\'' +
                ", solID='" + solID + '\'' +
                ", displayName='" + displayName + '\'' +
                ", adUserID='" + adUserID + '\'' +
                ", upmGroupCode='" + upmGroupCode + '\'' +
                ", upmID='" + upmID + '\'' +
                ", userType=" + userType +
                ", userRefID=" + userRefID +
                ", status=" + status +
                ", roles=" + roles +
                ", addedRoles=" + addedRoles +
                ", removedRoles=" + removedRoles +
                ", privileges=" + privileges +
                '}';
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
