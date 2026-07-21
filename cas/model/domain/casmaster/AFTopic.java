package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_AF_TOPIC")
public class AFTopic extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_TOPIC")
    @SequenceGenerator(name = "SEQ_T_AF_TOPIC", sequenceName = "SEQ_T_AF_TOPIC", allocationSize = 1)
    @Column(name = "AF_TOPIC_ID")
    private Integer topicID;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAGE")
    private DomainConstants.ApplicationFormTopicPage page;

    @Column(name = "TOPIC")
    private String topic;

    @Column(name = "TOPIC_CODE")
    private String topicCode;

    @Column(name = "TOPIC_DATA")
    private String topicData;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "topic")
    private Set<AFTopicUpcSection> afTopicUpcSectionSet;

    public Integer getTopicID() {
        return topicID;
    }

    public void setTopicID(Integer topicID) {
        this.topicID = topicID;
    }

    public DomainConstants.ApplicationFormTopicPage getPage() {
        return page;
    }

    public void setPage(DomainConstants.ApplicationFormTopicPage page) {
        this.page = page;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public String getTopicData() {
        return topicData;
    }

    public void setTopicData(String topicData) {
        this.topicData = topicData;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public Set<AFTopicUpcSection> getAfTopicUpcSectionSet() {
        if (afTopicUpcSectionSet == null) {
            this.afTopicUpcSectionSet = new HashSet<>();
        }
        return afTopicUpcSectionSet;
    }

    public void setAfTopicUpcSectionSet(Set<AFTopicUpcSection> afTopicUpcSectionSet) {
        this.afTopicUpcSectionSet = afTopicUpcSectionSet;
    }


    public List<AFTopicUpcSection> getOrderedAfTopicUpcSection() {
        return getAfTopicUpcSectionSet().stream().sorted(new Comparator<AFTopicUpcSection>() {
            @Override
            public int compare(AFTopicUpcSection o1, AFTopicUpcSection o2) {
                return o1.getTopicUpcSectionID().compareTo(o2.getTopicUpcSectionID());
            }
        }).collect(Collectors.toList());
    }

    public void addTopicUpcSection(AFTopicUpcSection afTopicUpcSection) {
        afTopicUpcSection.setTopic(this);
        this.getAfTopicUpcSectionSet().add(afTopicUpcSection);
    }

}
