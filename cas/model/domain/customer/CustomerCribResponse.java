package com.itechro.cas.model.domain.customer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_CUSTOMER_CRIB_RESPONSE")
public class CustomerCribResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CUSTOMER_CRIB_RESPONSE")
    @SequenceGenerator(name = "SEQ_T_CUSTOMER_CRIB_RESPONSE", sequenceName = "SEQ_T_CUSTOMER_CRIB_RESPONSE", allocationSize = 1)
    @Column(name = "CUSTOMER_CRIB_RESPONSE_ID")
    private Integer customerCribReportID;

    @Column(name = "NIC")
    private String nic;

    @Column(name = "BRN")
    private String brn;

    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "CUSTOMER_GENDER")
    private String customerGender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    public Integer getCustomerCribReportID() {
        return customerCribReportID;
    }

    public void setCustomerCribReportID(Integer customerCribReportID) {
        this.customerCribReportID = customerCribReportID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getBrn() {
        return brn;
    }

    public void setBrn(String brn) {
        this.brn = brn;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }
}
