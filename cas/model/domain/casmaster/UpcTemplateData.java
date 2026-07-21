package com.itechro.cas.model.domain.casmaster;

import javax.persistence.*;

@Entity
@Table(name = "T_UPC_TEMPLATE_DATA")
public class UpcTemplateData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_UPC_TEMPLATE_DATA")
    @SequenceGenerator(name = "SEQ_T_UPC_TEMPLATE_DATA", sequenceName = "SEQ_T_UPC_TEMPLATE_DATA", allocationSize = 1)
    @Column(name = "UPC_TEMPLATE_DATA_ID")
    private Integer upcTemplateDataID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPC_TEMPLATE_ID")
    private UpcTemplate upcTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPC_SECTION_ID")
    private UpcSection upcSection;

    @Column(name = "PARENT_SECTION_ID")
    private Integer parentSectionID;

    @Column(name = "SECTION_LEVEL")
    private Integer sectionLevel;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    public Integer getUpcTemplateDataID() {
        return upcTemplateDataID;
    }

    public void setUpcTemplateDataID(Integer upcTemplateDataID) {
        this.upcTemplateDataID = upcTemplateDataID;
    }

    public UpcTemplate getUpcTemplate() {
        return upcTemplate;
    }

    public void setUpcTemplate(UpcTemplate upcTemplate) {
        this.upcTemplate = upcTemplate;
    }

    public UpcSection getUpcSection() {
        return upcSection;
    }

    public void setUpcSection(UpcSection upcSection) {
        this.upcSection = upcSection;
    }

    public Integer getParentSectionID() {
        return parentSectionID;
    }

    public void setParentSectionID(Integer parentSectionID) {
        this.parentSectionID = parentSectionID;
    }

    public Integer getSectionLevel() {
        return sectionLevel;
    }

    public void setSectionLevel(Integer sectionLevel) {
        this.sectionLevel = sectionLevel;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
