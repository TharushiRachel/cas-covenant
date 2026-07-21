package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_UPC_TEMPLATE")
public class UpcTemplate extends ApprovableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_UPC_TEMPLATE")
    @SequenceGenerator(name = "SEQ_T_UPC_TEMPLATE", sequenceName = "SEQ_T_UPC_TEMPLATE", allocationSize = 1)
    @Column(name = "UPC_TEMPLATE_ID")
    private Integer upcTemplateID;

    @Column(name = "TEMPLATE_NAME")
    private String templateName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "UPC_LABEL")
    private String upcLabel;

    @Column(name = "UPC_LABEL_BACKGROUND_COLOR")
    private String upcLabelBackgroundColor;

    @Column(name = "UPC_LABEL_FONT_COLOR")
    private String upcLabelFontColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "NOTIFY_EMAILS")
    private String notifyEmails;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "upcTemplate")
    private Set<UpcTemplateData> upcTemplateDataSet;

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getUpcLabel() {
        return upcLabel;
    }

    public void setUpcLabel(String upcLabel) {
        this.upcLabel = upcLabel;
    }

    public String getUpcLabelBackgroundColor() {
        return upcLabelBackgroundColor;
    }

    public void setUpcLabelBackgroundColor(String upcLabelBackgroundColor) {
        this.upcLabelBackgroundColor = upcLabelBackgroundColor;
    }

    public String getUpcLabelFontColor() {
        return upcLabelFontColor;
    }

    public void setUpcLabelFontColor(String upcLabelFontColor) {
        this.upcLabelFontColor = upcLabelFontColor;
    }

    public Set<UpcTemplateData> getUpcTemplateDataSet() {
        if (upcTemplateDataSet == null) {
            upcTemplateDataSet = new HashSet<>();
        }
        return upcTemplateDataSet;
    }

    public void setUpcTemplateDataSet(Set<UpcTemplateData> upcTemplateDataSet) {
        this.upcTemplateDataSet = upcTemplateDataSet;
    }

    public UpcTemplateData getUpcTemplateDataByID(Integer upcTemplateID) {
        return this.getUpcTemplateDataSet().stream().
                filter(upcTemplateData  -> {
                    return upcTemplateID.equals(upcTemplateData.getUpcTemplateDataID());
                }).findFirst().orElse(null);
    }


    public void addUpcTemplateData(UpcTemplateData templateData) {
        templateData.setUpcTemplate(this);
        this.getUpcTemplateDataSet().add(templateData);
    }

    public String getNotifyEmails() {
        if (notifyEmails == null){
            return "";
        }
        return notifyEmails;
    }

    public void setNotifyEmails(String notifyEmails) {
        this.notifyEmails = notifyEmails;
    }
}
