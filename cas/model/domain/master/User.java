package com.itechro.cas.model.domain.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "T_USER")
public class User extends UserTrackableEntity {

    private static final long serialVersionUID = 6304703376537733758L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_USER")
    @SequenceGenerator(name = "SEQ_T_USER", sequenceName = "SEQ_T_USER", allocationSize = 1)
    @Column(name = "USER_ID")
    private Integer userID;

    @Column(name = "USER_NAME")
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "TITLE")
    private DomainConstants.Title title;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DOB")
    private Date dateOfBirth;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private DomainConstants.Gender gender;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CONTACT_NO")
    private String contactNo;

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE")
    private DomainConstants.UserType userType;

    @Column(name = "USER_REF_ID")
    private Integer userRefID;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "t_user_role", joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
    private Set<Role> roles;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Set<Role> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void removeRoles(List<Role> removeSet) {
        if (removeSet != null && removeSet.size() > 0) {
            this.getRoles().removeAll(removeSet);
        }
    }

    public String getFullName() {
        if (this.getTitle() != null) {
            return this.getTitle().getDescription() + " " + this.getFirstName() + " " + this.getLastName();
        }
        return null;
    }

    public boolean isAgent() {
        return this.userType == DomainConstants.UserType.AGENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return getUserName() != null ? getUserName().equals(user.getUserName()) : user.getUserName() == null;
    }

    @Override
    public int hashCode() {
        return getUserName() != null ? getUserName().hashCode() : 0;
    }
}
