package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_TOPIC_UPC_SECTION")
public class AFTopicUpcSection {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_TOPIC_UPC_SECTION")
    @SequenceGenerator(name = "SEQ_T_AF_TOPIC_UPC_SECTION", sequenceName = "SEQ_T_AF_TOPIC_UPC_SECTION", allocationSize = 1)
    @Column(name = "AF_TOPIC_UPC_SECTION_ID")
    private Integer topicUpcSectionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AF_TOPIC_ID")
    private AFTopic topic;

    @Column(name = "UPC_SECTION_ID")
    private Integer upcSectionID;

    @Column(name = "UPC_SECTION_NAME")
    private String upcSectionName;

    @Column(name = "UPC_TEMPLATE_ID")
    private Integer upcTemplateID;

    @Column(name = "UPC_TEMPLATE_NAME")
    private String upcTemplateName;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getTopicUpcSectionID() {
        return topicUpcSectionID;
    }

    public void setTopicUpcSectionID(Integer topicUpcSectionID) {
        this.topicUpcSectionID = topicUpcSectionID;
    }

    public AFTopic getTopic() {
        return topic;
    }

    public void setTopic(AFTopic topic) {
        this.topic = topic;
    }

    public Integer getUpcSectionID() {
        return upcSectionID;
    }

    public void setUpcSectionID(Integer upcSectionID) {
        this.upcSectionID = upcSectionID;
    }

    public String getUpcSectionName() {
        return upcSectionName;
    }

    public void setUpcSectionName(String upcSectionName) {
        this.upcSectionName = upcSectionName;
    }

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
    }

    public String getUpcTemplateName() {
        return upcTemplateName;
    }

    public void setUpcTemplateName(String upcTemplateName) {
        this.upcTemplateName = upcTemplateName;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
