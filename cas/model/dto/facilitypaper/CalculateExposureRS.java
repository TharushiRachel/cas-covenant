package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.util.DecimalCalculator;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculateExposureRS implements Serializable {

    private Facility facility;

    private BigDecimal totalDirectExposurePrevious;

    private BigDecimal totalDirectExposureNew;

    private BigDecimal totalIndirectExposurePrevious;

    private BigDecimal totalIndirectExposureNew;

    private BigDecimal totalExposurePrevious;

    private BigDecimal totalExposureNew;

    //  For Three Column Facilities
    private BigDecimal totalIndirectExposureExisting;

    //  For Three Column Facilities
    private BigDecimal totalDirectExposureExisting;

    //  For Three Column Facilities
    private BigDecimal totalExposureExisting;

    //  For Three Column Facilities
    private BigDecimal netTotalExposureNew;

    //  For Three Column Facilities
    private BigDecimal netTotalExposurePrevious;

    //  For Three Column Facilities
    private BigDecimal netTotalExposureExisting;

    //  For Three Column Facilities
    private BigDecimal existingCashMargin;

    //  For Three Column Facilities
    private BigDecimal outstandingCashMargin;

    //  For Three Column Facilities
    private BigDecimal proposedCashMargin;

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public BigDecimal getTotalDirectExposurePrevious() {
        if (totalDirectExposurePrevious == null) {
            totalDirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposurePrevious;
    }

    public void setTotalDirectExposurePrevious(BigDecimal totalDirectExposurePrevious) {
        this.totalDirectExposurePrevious = totalDirectExposurePrevious;
    }

    public void addTotalDirectExposurePrevious(BigDecimal value) {
        this.totalDirectExposurePrevious = DecimalCalculator.add(this.getTotalDirectExposurePrevious(), value);
    }

    public BigDecimal getTotalDirectExposureNew() {
        if (totalDirectExposureNew == null) {
            totalDirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposureNew;
    }

    public void setTotalDirectExposureNew(BigDecimal totalDirectExposureNew) {
        this.totalDirectExposureNew = totalDirectExposureNew;
    }

    public void addTotalDirectExposureNew(BigDecimal value) {
        this.totalDirectExposureNew = DecimalCalculator.add(this.getTotalDirectExposureNew(), value);
    }

    public BigDecimal getTotalIndirectExposurePrevious() {
        if (totalIndirectExposurePrevious == null) {
            totalIndirectExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposurePrevious;
    }

    public void setTotalIndirectExposurePrevious(BigDecimal totalIndirectExposurePrevious) {
        this.totalIndirectExposurePrevious = totalIndirectExposurePrevious;
    }

    public void addTotalIndirectExposurePrevious(BigDecimal value) {
        this.totalIndirectExposurePrevious = DecimalCalculator.add(this.getTotalIndirectExposurePrevious(), value);
    }

    public BigDecimal getTotalIndirectExposureNew() {
        if (totalIndirectExposureNew == null) {
            totalIndirectExposureNew = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposureNew;
    }

    public void setTotalIndirectExposureNew(BigDecimal totalIndirectExposureNew) {
        this.totalIndirectExposureNew = totalIndirectExposureNew;
    }

    public void addTotalIndirectExposureNew(BigDecimal value) {
        this.totalIndirectExposureNew = DecimalCalculator.add(this.getTotalIndirectExposureNew(), value);
    }

    public BigDecimal getTotalExposurePrevious() {
        if (totalExposurePrevious == null) {
            totalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return totalExposurePrevious;
    }

    public void setTotalExposurePrevious(BigDecimal totalExposurePrevious) {
        this.totalExposurePrevious = totalExposurePrevious;
    }

    public void addTotalExposurePrevious(BigDecimal value) {
        this.totalExposurePrevious = DecimalCalculator.add(this.getTotalExposurePrevious(), value);
    }

    public BigDecimal getTotalExposureNew() {
        if (totalExposureNew == null) {
            totalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return totalExposureNew;
    }

    public void setTotalExposureNew(BigDecimal totalExposureNew) {
        this.totalExposureNew = totalExposureNew;
    }

    public void addTotalExposureNew(BigDecimal value) {
        this.totalExposureNew = DecimalCalculator.add(this.getTotalExposureNew(), value);
    }

    public BigDecimal getTotalIndirectExposureExisting() {
        if (totalIndirectExposureExisting == null) {
            this.totalIndirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalIndirectExposureExisting;
    }

    public void setTotalIndirectExposureExisting(BigDecimal totalIndirectExposureExisting) {
        this.totalIndirectExposureExisting = totalIndirectExposureExisting;
    }

    public void addTotalIndirectExposureExisting(BigDecimal value) {
        this.totalIndirectExposureExisting = DecimalCalculator.add(this.getTotalIndirectExposureExisting(), value);
    }

    public BigDecimal getTotalDirectExposureExisting() {
        if (totalDirectExposureExisting == null) {
            totalDirectExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalDirectExposureExisting;
    }

    public void setTotalDirectExposureExisting(BigDecimal totalDirectExposureExisting) {
        this.totalDirectExposureExisting = totalDirectExposureExisting;
    }

    public void addTotalDirectExposureExisting(BigDecimal value) {
        this.totalDirectExposureExisting = DecimalCalculator.add(this.getTotalDirectExposureExisting(), value);
    }

    public BigDecimal getTotalExposureExisting() {
        if (totalExposureExisting == null) {
            this.totalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return totalExposureExisting;
    }

    public void setTotalExposureExisting(BigDecimal totalExposureExisting) {
        this.totalExposureExisting = totalExposureExisting;
    }

    public void addTotalExposureExisting(BigDecimal value) {
        this.totalExposureExisting = DecimalCalculator.add(this.getTotalExposureExisting(), value);
    }

    public BigDecimal getNetTotalExposureNew() {
        if (netTotalExposureNew == null) {
            this.netTotalExposureNew = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposureNew;
    }

    public void setNetTotalExposureNew(BigDecimal netTotalExposureNew) {
        this.netTotalExposureNew = netTotalExposureNew;
    }

    public void addNetTotalExposureNew(BigDecimal value) {
        this.netTotalExposureNew = DecimalCalculator.add(this.getNetTotalExposureNew(), value);
    }

    public void subtractNetTotalExposureNew(BigDecimal value) {
        this.netTotalExposureNew = DecimalCalculator.subtract(this.getNetTotalExposureNew(), value);
    }

    public BigDecimal getNetTotalExposurePrevious() {
        if (netTotalExposurePrevious == null) {
            this.netTotalExposurePrevious = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposurePrevious;
    }

    public void setNetTotalExposurePrevious(BigDecimal netTotalExposurePrevious) {
        this.netTotalExposurePrevious = netTotalExposurePrevious;
    }

    public void addNetTotalExposurePrevious(BigDecimal value) {
        this.netTotalExposurePrevious = DecimalCalculator.add(this.getNetTotalExposurePrevious(), value);
    }

    public void subtractNetTotalExposurePrevious(BigDecimal value) {
        this.netTotalExposurePrevious = DecimalCalculator.subtract(this.getNetTotalExposurePrevious(), value);
    }

    public BigDecimal getNetTotalExposureExisting() {
        if (netTotalExposureExisting == null) {
            this.netTotalExposureExisting = DecimalCalculator.getDefaultZero();
        }
        return netTotalExposureExisting;
    }

    public void setNetTotalExposureExisting(BigDecimal netTotalExposureExisting) {
        this.netTotalExposureExisting = netTotalExposureExisting;
    }

    public void addNetTotalExposureExisting(BigDecimal value) {
        this.netTotalExposureExisting = DecimalCalculator.add(this.getNetTotalExposureExisting(), value);
    }

    public void subtractNetTotalExposureExisting(BigDecimal value) {
        this.netTotalExposureExisting = DecimalCalculator.subtract(this.getNetTotalExposureExisting(), value);
    }

    public BigDecimal getExistingCashMargin() {
        return existingCashMargin;
    }

    public void setExistingCashMargin(BigDecimal existingCashMargin) {
        this.existingCashMargin = existingCashMargin;
    }

    public BigDecimal getOutstandingCashMargin() {
        return outstandingCashMargin;
    }

    public void setOutstandingCashMargin(BigDecimal outstandingCashMargin) {
        this.outstandingCashMargin = outstandingCashMargin;
    }

    public BigDecimal getProposedCashMargin() {
        return proposedCashMargin;
    }

    public void setProposedCashMargin(BigDecimal proposedCashMargin) {
        this.proposedCashMargin = proposedCashMargin;
    }

    @Override
    public String toString() {
        return "CalculateExposureRS{" +
                "totalDirectExposurePrevious=" + totalDirectExposurePrevious +
                ", totalDirectExposureNew=" + totalDirectExposureNew +
                ", totalIndirectExposurePrevious=" + totalIndirectExposurePrevious +
                ", totalIndirectExposureNew=" + totalIndirectExposureNew +
                ", totalExposurePrevious=" + totalExposurePrevious +
                ", totalExposureNew=" + totalExposureNew +
                ", totalIndirectExposureExisting=" + totalIndirectExposureExisting +
                ", totalDirectExposureExisting=" + totalDirectExposureExisting +
                ", totalExposureExisting=" + totalExposureExisting +
                ", netTotalExposureNew=" + netTotalExposureNew +
                ", netTotalExposurePrevious=" + netTotalExposurePrevious +
                ", netTotalExposureExisting=" + netTotalExposureExisting +
                ", existingCashMargin=" + existingCashMargin +
                ", outstandingCashMargin=" + outstandingCashMargin +
                ", proposedCashMargin=" + proposedCashMargin +
                '}';
    }
}
