package com.itechro.cas.service.faclititypaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.facilitypaper.CalculateExposureRS;
import com.itechro.cas.util.CasCustomCollectionUtil;
import com.itechro.cas.util.DecimalCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class FacilityPaperUtils {

    private FacilityPaperJdbcDao facilityPaperJdbcDao;

    private Map<Facility, Set<Facility>> parentFacilityWiseChildFacilityListMap = new HashMap<>();

    public FacilityPaperUtils(FacilityPaperJdbcDao facilityPaperJdbcDao) {
        this.facilityPaperJdbcDao = facilityPaperJdbcDao;
    }

    public FacilityPaper calculateFacilityPaperExposure(FacilityPaper facilityPaper) {
        if (facilityPaper.getIsCommittee().equals(AppsConstants.YesNo.Y)) {
            return this.calculateFacilityPaperCorporateExposure(facilityPaper);
        } else {
            return this.calculateFacilityPaperNonCorporateExposure(facilityPaper);
        }
    }

    private FacilityPaper calculateFacilityPaperNonCorporateExposure(FacilityPaper facilityPaper) {

        CalculateExposureRS calculateExposureRS = new CalculateExposureRS();
        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposureNewValues = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposureNewValues = new HashMap<>();

        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposurePrevValues = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposurePrevValues = new HashMap<>();


        for (Facility facility : facilityPaper.getOrderedActiveFacilityList()) {
            Set<Facility> childFacilitySet = new HashSet<>();
            childFacilitySet = getChildFacilitySet(facilityPaper.getActiveFacility(), facility, childFacilitySet);
            Facility rootFacility = getRootParentFacility(facilityPaper.getActiveFacility(), facility);
            parentFacilityWiseChildFacilityListMap.putIfAbsent(rootFacility, new HashSet<>());
            if (childFacilitySet.size() > 0 && rootFacility.getFacilityID() != null) {
                parentFacilityWiseChildFacilityListMap.put(rootFacility, CasCustomCollectionUtil.mergeSet(parentFacilityWiseChildFacilityListMap.get(rootFacility), childFacilitySet));
            }

            if (facility.getParentFacilityID() == null) {
                calculateExposureRS.addTotalExposureNew(facility.getFacilityAmount());
                calculateExposureRS.addTotalExposurePrevious(facility.getOutstandingAmount());
            }
        }

        for (Facility parentFacility : parentFacilityWiseChildFacilityListMap.keySet()) {

            if (parentFacility.getIsNew() != null && parentFacility.getIsNew().getBoolVal()) {
                if (parentFacility.getDirectFacility() != null && parentFacility.getDirectFacility().getBoolVal()) {
                    parentFacilityIDWiseDirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacility.getFacilityAmount());
                } else {
                    parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacility.getFacilityAmount());
                }

                for (Facility childFacility : parentFacilityWiseChildFacilityListMap.get(parentFacility)) {
                    if (parentFacility.getDirectFacility() != null && parentFacility.getDirectFacility().equals(childFacility.getDirectFacility())) {
                        if (childFacility.getDirectFacility() != null && childFacility.getDirectFacility().getBoolVal()) {
                            if (parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()) != null) {
                                parentFacilityIDWiseDirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()).max(childFacility.getFacilityAmount()));
                            }
                        } else {
                            if (parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()) != null) {
                                parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()).max(childFacility.getFacilityAmount()));
                            }
                        }
                    } else {
                        if (childFacility.getParentFacilityID().equals(parentFacility.getFacilityID())) {
                            if (childFacility.getDirectFacility() != null && childFacility.getDirectFacility().getBoolVal()) {
                                parentFacilityIDWiseDirectExposureNewValues.put(parentFacility.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()), childFacility.getFacilityAmount()));
                            } else {
                                parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()), childFacility.getFacilityAmount()));
                            }

                        } else {
                            if (childFacility.getDirectFacility() != null && childFacility.getDirectFacility().getBoolVal()) {
                                if (parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()) != null) {
                                    parentFacilityIDWiseDirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()).max(childFacility.getFacilityAmount()));
                                } else {
                                    parentFacilityIDWiseDirectExposureNewValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getFacilityAmount());
                                }
                            } else {
                                if (parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()) != null) {
                                    parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()).max(childFacility.getFacilityAmount()));
                                } else {
                                    parentFacilityIDWiseIndirectExposureNewValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getFacilityAmount());
                                }
                            }
                        }
                    }
                }

            } else {
                if (parentFacility.getDirectFacility() != null && parentFacility.getDirectFacility().getBoolVal()) {
                    parentFacilityIDWiseDirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacility.getFacilityAmount());
                    parentFacilityIDWiseDirectExposurePrevValues.put(parentFacility.getFacilityID(), parentFacility.getOutstandingAmount());
                } else {
                    parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacility.getFacilityAmount());
                    parentFacilityIDWiseIndirectExposurePrevValues.put(parentFacility.getFacilityID(), parentFacility.getOutstandingAmount());
                }

                for (Facility childFacility : parentFacilityWiseChildFacilityListMap.get(parentFacility)) {
                    if (parentFacility.getDirectFacility() != null && parentFacility.getDirectFacility().equals(childFacility.getDirectFacility())) {
                        if (parentFacility.getDirectFacility() != null && parentFacility.getDirectFacility().getBoolVal()) {
                            if (parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()) != null) {
                                parentFacilityIDWiseDirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()).max(childFacility.getFacilityAmount()));
                            } else {
                                parentFacilityIDWiseDirectExposureNewValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getFacilityAmount());
                            }
                            if (parentFacilityIDWiseDirectExposurePrevValues.get(parentFacility.getFacilityID()) != null) {
                                parentFacilityIDWiseDirectExposurePrevValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseDirectExposurePrevValues.get(parentFacility.getFacilityID()).max(childFacility.getOutstandingAmount()));
                            } else {
                                parentFacilityIDWiseDirectExposurePrevValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getOutstandingAmount());
                            }
                        } else {
                            if (parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()) != null) {
                                parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()).max(childFacility.getFacilityAmount()));
                            } else {
                                parentFacilityIDWiseIndirectExposureNewValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getFacilityAmount());
                            }
                            if (parentFacilityIDWiseIndirectExposurePrevValues.get(parentFacility.getFacilityID()) != null) {
                                parentFacilityIDWiseIndirectExposurePrevValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseIndirectExposurePrevValues.get(parentFacility.getFacilityID()).max(childFacility.getOutstandingAmount()));
                            } else {
                                parentFacilityIDWiseIndirectExposurePrevValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getOutstandingAmount());
                            }
                        }
                    } else {
                        if (childFacility.getParentFacilityID().equals(parentFacility.getFacilityID())) {
                            if (childFacility.getDirectFacility() != null && childFacility.getDirectFacility().getBoolVal()) {
                                parentFacilityIDWiseDirectExposureNewValues.put(parentFacility.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()), childFacility.getFacilityAmount()));
                                parentFacilityIDWiseDirectExposurePrevValues.put(parentFacility.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseDirectExposurePrevValues.get(parentFacility.getFacilityID()), childFacility.getOutstandingAmount()));
                            } else {
                                parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()), childFacility.getFacilityAmount()));
                                parentFacilityIDWiseIndirectExposurePrevValues.put(parentFacility.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseIndirectExposurePrevValues.get(parentFacility.getFacilityID()), childFacility.getOutstandingAmount()));
                            }
                        } else {
                            if (childFacility.getDirectFacility() != null && childFacility.getDirectFacility().getBoolVal()) {
                                if (parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()) != null) {
                                    parentFacilityIDWiseDirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(parentFacility.getFacilityID()).max(childFacility.getFacilityAmount()));
                                } else {
                                    parentFacilityIDWiseDirectExposureNewValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getFacilityAmount());
                                }

                                if (parentFacilityIDWiseDirectExposurePrevValues.get(parentFacility.getFacilityID()) != null) {
                                    parentFacilityIDWiseDirectExposurePrevValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseDirectExposurePrevValues.get(parentFacility.getFacilityID()).max(childFacility.getOutstandingAmount()));
                                } else {
                                    parentFacilityIDWiseDirectExposurePrevValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getOutstandingAmount());
                                }
                            } else {
                                if (parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()) != null) {
                                    parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getFacilityID()).max(childFacility.getFacilityAmount()));
                                } else {
                                    parentFacilityIDWiseIndirectExposureNewValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getFacilityAmount());
                                }

                                if (parentFacilityIDWiseIndirectExposurePrevValues.get(parentFacility.getFacilityID()) != null) {
                                    parentFacilityIDWiseIndirectExposurePrevValues.put(parentFacility.getFacilityID(), parentFacilityIDWiseIndirectExposurePrevValues.get(parentFacility.getFacilityID()).max(childFacility.getOutstandingAmount()));
                                } else {
                                    parentFacilityIDWiseIndirectExposurePrevValues.putIfAbsent(parentFacility.getFacilityID(), childFacility.getOutstandingAmount());
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Integer facilityID : parentFacilityIDWiseDirectExposureNewValues.keySet()) {
            calculateExposureRS.addTotalDirectExposureNew(parentFacilityIDWiseDirectExposureNewValues.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseIndirectExposureNewValues.keySet()) {
            calculateExposureRS.addTotalIndirectExposureNew(parentFacilityIDWiseIndirectExposureNewValues.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseDirectExposurePrevValues.keySet()) {
            calculateExposureRS.addTotalDirectExposurePrevious(parentFacilityIDWiseDirectExposurePrevValues.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseIndirectExposurePrevValues.keySet()) {
            calculateExposureRS.addTotalIndirectExposurePrevious(parentFacilityIDWiseIndirectExposurePrevValues.get(facilityID));
        }

        facilityPaper.setTotalDirectExposurePrevious(calculateExposureRS.getTotalDirectExposurePrevious());
        facilityPaper.setTotalDirectExposureNew(calculateExposureRS.getTotalDirectExposureNew());
        facilityPaper.setTotalIndirectExposurePrevious(calculateExposureRS.getTotalIndirectExposurePrevious());
        facilityPaper.setTotalIndirectExposureNew(calculateExposureRS.getTotalIndirectExposureNew());
        facilityPaper.setTotalExposurePrevious(calculateExposureRS.getTotalExposurePrevious());
        facilityPaper.setTotalExposureNew(calculateExposureRS.getTotalExposureNew());

        return facilityPaper;
    }


//    private FacilityPaper calculateFacilityPaperNonCorporateExposure(FacilityPaper facilityPaper) {
//        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposureNewValues = new HashMap<>();
//        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposureNewValues = new HashMap<>();
//
//        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposurePrevValues = new HashMap<>();
//        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposurePrevValues = new HashMap<>();
//        CalculateExposureRS calculateExposureRS = new CalculateExposureRS();
//        for (Facility facility : facilityPaper.getActiveFacility()) {
//            Facility parentFacility = null;
//            Facility grandParentFacility = null;
//            if (facility.getParentFacilityID() != null) {
//                parentFacility = facilityPaper.getFacilityByID(facility.getParentFacilityID());
//                if (parentFacility.getParentFacilityID() != null) {
//                    grandParentFacility = facilityPaper.getFacilityByID(parentFacility.getParentFacilityID());
//                }
//            }
//            Facility rootParentFacility = getRootParentFacility(facilityPaper.getActiveFacility(), facility);
//
//
//            if (facility.getIsNew() != null && facility.getIsNew().getBoolVal()) {
//                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//
//                    if (facility.getParentFacilityID() == null) {
//                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
//                    } else if (facility.getParentFacilityID() != null) {
//                        parentFacilityIDWiseDirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
//                    }
//
//                } else {
//                    if (facility.getParentFacilityID() == null) {
//                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
//                    } else if (facility.getParentFacilityID() != null) {
//                        parentFacilityIDWiseIndirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
//                    }
//                }
//                if (facility.getParentFacilityID() == null) {
//                    calculateExposureRS.addTotalExposureNew(facility.getFacilityAmount());
//                }
//            } else {
//                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//
//                    if (facility.getParentFacilityID() == null) {
//                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
//                        parentFacilityIDWiseDirectExposurePrevValues.put(facility.getFacilityID(), facility.getOutstandingAmount());
//                    } else if (facility.getParentFacilityID() != null) {
//                        parentFacilityIDWiseDirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
//
//                        parentFacilityIDWiseDirectExposurePrevValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseDirectExposurePrevValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposurePrevValues.get(facility.getParentFacilityID()).max(facility.getOutstandingAmount()));
//                    }
//                } else {
//                    if (facility.getParentFacilityID() == null) {
//                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
//                        parentFacilityIDWiseIndirectExposurePrevValues.put(facility.getFacilityID(), facility.getOutstandingAmount());
//                    } else if (facility.getParentFacilityID() != null) {
//                        parentFacilityIDWiseIndirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseIndirectExposurePrevValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        if (parentFacility != null && parentFacility.getDirectFacility().equals(facility.getDirectFacility())) {
//                            if (grandParentFacility != null && !grandParentFacility.getDirectFacility().equals(parentFacility.getDirectFacility())) {
//                                parentFacilityIDWiseIndirectExposureNewValues.put(parentFacility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(parentFacility.getParentFacilityID()).max(facility.getFacilityAmount()));
//                                parentFacilityIDWiseIndirectExposurePrevValues.put(parentFacility.getParentFacilityID(), parentFacilityIDWiseIndirectExposurePrevValues.get(parentFacility.getParentFacilityID()).max(facility.getOutstandingAmount()));
//                            } else {
//                                parentFacilityIDWiseIndirectExposureNewValues.put(rootParentFacility.getFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(rootParentFacility.getFacilityID()).max(facility.getFacilityAmount()));
//                                parentFacilityIDWiseIndirectExposurePrevValues.put(rootParentFacility.getFacilityID(), parentFacilityIDWiseIndirectExposurePrevValues.get(rootParentFacility.getFacilityID()).max(facility.getOutstandingAmount()));
//                            }
//                        } else {
//                            parentFacilityIDWiseIndirectExposureNewValues.put(facility.getParentFacilityID(), DecimalCalculator.add(parentFacilityIDWiseIndirectExposureNewValues.get(facility.getParentFacilityID()), facility.getFacilityAmount()));
//                            parentFacilityIDWiseIndirectExposurePrevValues.put(facility.getParentFacilityID(), DecimalCalculator.add(parentFacilityIDWiseIndirectExposurePrevValues.get(facility.getParentFacilityID()), facility.getOutstandingAmount()));
//                        }
//                    }
//                }
//                if (facility.getParentFacilityID() == null) {
//                    calculateExposureRS.addTotalExposureNew(facility.getFacilityAmount());
//                    calculateExposureRS.addTotalExposurePrevious(facility.getOutstandingAmount());
//                }
//            }
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseDirectExposureNewValues.keySet()) {
//            calculateExposureRS.addTotalDirectExposureNew(parentFacilityIDWiseDirectExposureNewValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseIndirectExposureNewValues.keySet()) {
//            calculateExposureRS.addTotalIndirectExposureNew(parentFacilityIDWiseIndirectExposureNewValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseDirectExposurePrevValues.keySet()) {
//            calculateExposureRS.addTotalDirectExposurePrevious(parentFacilityIDWiseDirectExposurePrevValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseIndirectExposurePrevValues.keySet()) {
//            calculateExposureRS.addTotalIndirectExposurePrevious(parentFacilityIDWiseIndirectExposurePrevValues.get(facilityID));
//        }
//
//
//        facilityPaper.setTotalDirectExposurePrevious(calculateExposureRS.getTotalDirectExposurePrevious());
//        facilityPaper.setTotalDirectExposureNew(calculateExposureRS.getTotalDirectExposureNew());
//        facilityPaper.setTotalIndirectExposurePrevious(calculateExposureRS.getTotalIndirectExposurePrevious());
//        facilityPaper.setTotalIndirectExposureNew(calculateExposureRS.getTotalIndirectExposureNew());
//        facilityPaper.setTotalExposurePrevious(calculateExposureRS.getTotalExposurePrevious());
//        facilityPaper.setTotalExposureNew(calculateExposureRS.getTotalExposureNew());
//
//        return facilityPaper;
//    }


    private Facility getRootParentFacility(Set<Facility> facilities, Facility facility) {

        if (facility.getParentFacilityID() == null) {
            return facility;
        } else {
            Facility parentFacility = facilities.stream().filter(f -> f.getFacilityID() != null && f.getFacilityID().equals(facility.getParentFacilityID())).findAny().orElse(facility);
            return getRootParentFacility(facilities, parentFacility);
        }
    }

    private Set<Facility> getChildFacilitySet(Set<Facility> facilities, Facility facility, Set<Facility> childFacilities) {

        if (facility.getParentFacilityID() == null) {
            return childFacilities == null ? new HashSet<>() : childFacilities;
        } else {
            Facility parentFacility = facilities.stream().filter(f -> f.getFacilityID() != null && f.getFacilityID().equals(facility.getParentFacilityID())).findAny().orElse(facility);
            childFacilities.add(facility);
            return getChildFacilitySet(facilities, parentFacility, childFacilities);
        }
    }

//    private List<Facility> getParentFacilityList(Set<Facility> facilities, Facility facility, List<Facility> parenFacilityList) {
//        if (facility.getParentFacilityID() == null) {
//            parenFacilityList.add(facility);
//            return parenFacilityList;
//        } else {
//            Facility parentFacility = facilities.stream().filter(f -> f.getFacilityID() != null && f.getFacilityID().equals(facility.getParentFacilityID())).findAny().orElse(facility);
//            parenFacilityList.add(parentFacility);
//            return getParentFacilityList(facilities, parentFacility, parenFacilityList);
//        }
//    }

    private Facility getGrandParentFacility(Set<Facility> facilities, Facility facility) {

        if (facility.getParentFacilityID() == null) {
            return facility;
        } else {
            Facility parentFacility = facilities.stream().filter(f -> f.getFacilityID() != null && f.getFacilityID().equals(facility.getParentFacilityID())).findAny().orElse(facility);
            if (!facility.getDirectFacility().equals(parentFacility.getDirectFacility())) {
                return parentFacility;
            }
            return getGrandParentFacility(facilities, parentFacility);
        }
    }


//    private FacilityPaper calculateFacilityPaperNonCorporateExposure(FacilityPaper facilityPaper) {
//        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposureNewValues = new HashMap<>();
//        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposureNewValues = new HashMap<>();
//
//        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposurePrevValues = new HashMap<>();
//        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposurePrevValues = new HashMap<>();
//        CalculateExposureRS calculateExposureRS = new CalculateExposureRS();
//        for (Facility facility : facilityPaper.getActiveFacility()) {
//
//            CalculateExposureRS exposureRS = new CalculateExposureRS();
//            exposureRS = this.getExposureValues(facilityPaper.getActiveFacility(), facility, exposureRS);
//
//            parentFacilityIDWiseDirectExposureNewValues.putIfAbsent(exposureRS.getFacility().getFacilityID(), DecimalCalculator.getDefaultZero());
//            parentFacilityIDWiseIndirectExposureNewValues.putIfAbsent(exposureRS.getFacility().getFacilityID(), DecimalCalculator.getDefaultZero());
//            parentFacilityIDWiseDirectExposurePrevValues.putIfAbsent(exposureRS.getFacility().getFacilityID(), DecimalCalculator.getDefaultZero());
//            parentFacilityIDWiseIndirectExposurePrevValues.putIfAbsent(exposureRS.getFacility().getFacilityID(), DecimalCalculator.getDefaultZero());
//
//            parentFacilityIDWiseDirectExposureNewValues.put(exposureRS.getFacility().getFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(exposureRS.getFacility().getFacilityID()).max(exposureRS.getTotalDirectExposureNew()));
//            parentFacilityIDWiseIndirectExposureNewValues.put(exposureRS.getFacility().getFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(exposureRS.getFacility().getFacilityID()).max(exposureRS.getTotalIndirectExposureNew()));
//            parentFacilityIDWiseDirectExposurePrevValues.put(exposureRS.getFacility().getFacilityID(), parentFacilityIDWiseDirectExposurePrevValues.get(exposureRS.getFacility().getFacilityID()).max(exposureRS.getTotalDirectExposurePrevious()));
//            parentFacilityIDWiseIndirectExposurePrevValues.put(exposureRS.getFacility().getFacilityID(), parentFacilityIDWiseIndirectExposurePrevValues.get(exposureRS.getFacility().getFacilityID()).max(exposureRS.getTotalIndirectExposurePrevious()));
//
////            parentFacilityIDWiseDirectExposureNewValues.put(exposureRS.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseDirectExposureNewValues.get(exposureRS.getFacilityID()), exposureRS.getTotalDirectExposureNew()));
////            parentFacilityIDWiseIndirectExposureNewValues.put(exposureRS.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseIndirectExposureNewValues.get(exposureRS.getFacilityID()),exposureRS.getTotalIndirectExposureNew()));
////            parentFacilityIDWiseDirectExposurePrevValues.put(exposureRS.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseDirectExposurePrevValues.get(exposureRS.getFacilityID()),exposureRS.getTotalDirectExposurePrevious()));
////            parentFacilityIDWiseIndirectExposurePrevValues.put(exposureRS.getFacilityID(), DecimalCalculator.add(parentFacilityIDWiseIndirectExposurePrevValues.get(exposureRS.getFacilityID()),exposureRS.getTotalIndirectExposurePrevious()));
//
//
//            if (facility.getParentFacilityID() == null) {
//                calculateExposureRS.addTotalExposureNew(facility.getFacilityAmount());
//                calculateExposureRS.addTotalExposurePrevious(facility.getOutstandingAmount());
//            }
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseDirectExposureNewValues.keySet()) {
//            calculateExposureRS.addTotalDirectExposureNew(parentFacilityIDWiseDirectExposureNewValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseIndirectExposureNewValues.keySet()) {
//            calculateExposureRS.addTotalIndirectExposureNew(parentFacilityIDWiseIndirectExposureNewValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseDirectExposurePrevValues.keySet()) {
//            calculateExposureRS.addTotalDirectExposurePrevious(parentFacilityIDWiseDirectExposurePrevValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseIndirectExposurePrevValues.keySet()) {
//            calculateExposureRS.addTotalIndirectExposurePrevious(parentFacilityIDWiseIndirectExposurePrevValues.get(facilityID));
//        }
//
//        facilityPaper.setTotalDirectExposurePrevious(calculateExposureRS.getTotalDirectExposurePrevious());
//        facilityPaper.setTotalDirectExposureNew(calculateExposureRS.getTotalDirectExposureNew());
//        facilityPaper.setTotalIndirectExposurePrevious(calculateExposureRS.getTotalIndirectExposurePrevious());
//        facilityPaper.setTotalIndirectExposureNew(calculateExposureRS.getTotalIndirectExposureNew());
//        facilityPaper.setTotalExposurePrevious(calculateExposureRS.getTotalExposurePrevious());
//        facilityPaper.setTotalExposureNew(calculateExposureRS.getTotalExposureNew());
//
//        return facilityPaper;
//    }

//    private CalculateExposureRS getExposureValues(Set<Facility> facilities, Facility facility, CalculateExposureRS calculateExposureRS) {
//
//        if (facility.getParentFacilityID() == null) {
//            if (facility.getIsNew() != null && facility.getIsNew().getBoolVal()) {
//                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//                    calculateExposureRS.addTotalDirectExposureNew(calculateExposureRS.getTotalDirectExposureNew().max(facility.getFacilityAmount()));
//                } else {
//                    calculateExposureRS.addTotalIndirectExposureNew(calculateExposureRS.getTotalIndirectExposureNew().max(facility.getFacilityAmount()));
//                }
//            } else {
//                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//                    calculateExposureRS.addTotalDirectExposureNew(calculateExposureRS.getTotalDirectExposureNew().max(facility.getFacilityAmount()));
//                    calculateExposureRS.addTotalDirectExposurePrevious(calculateExposureRS.getTotalDirectExposurePrevious().max(facility.getOutstandingAmount()));
//                } else {
//                    calculateExposureRS.addTotalIndirectExposureNew(calculateExposureRS.getTotalIndirectExposureNew().max(facility.getFacilityAmount()));
//                    calculateExposureRS.addTotalIndirectExposurePrevious(calculateExposureRS.getTotalIndirectExposurePrevious().max(facility.getOutstandingAmount()));
//                }
//            }
//            calculateExposureRS.setFacility(facility);
//            return calculateExposureRS;
//        } else {
//            Facility parentFacility = facilities.stream().filter(f -> f.getFacilityID() != null && f.getFacilityID().equals(facility.getParentFacilityID())).findAny().orElse(facility);
//            if (!parentFacility.getDirectFacility().equals(facility.getDirectFacility())) {
//
//                if (facility.getIsNew() != null && facility.getIsNew().getBoolVal()) {
//                    if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//                        calculateExposureRS.addTotalDirectExposureNew(DecimalCalculator.add((calculateExposureRS.getTotalDirectExposureNew()), facility.getFacilityAmount()));
//                    } else {
//                        calculateExposureRS.addTotalIndirectExposureNew(DecimalCalculator.add(calculateExposureRS.getTotalIndirectExposureNew(), facility.getFacilityAmount()));
//                    }
//                } else {
//                    if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//                        calculateExposureRS.addTotalDirectExposureNew(DecimalCalculator.add(calculateExposureRS.getTotalDirectExposureNew(), facility.getFacilityAmount()));
//                        calculateExposureRS.addTotalDirectExposurePrevious(DecimalCalculator.add(calculateExposureRS.getTotalDirectExposurePrevious(), facility.getOutstandingAmount()));
//                    } else {
//                        calculateExposureRS.addTotalIndirectExposureNew(DecimalCalculator.add(calculateExposureRS.getTotalIndirectExposureNew(), (facility.getFacilityAmount())));
//                        calculateExposureRS.addTotalIndirectExposurePrevious(DecimalCalculator.add(calculateExposureRS.getTotalIndirectExposurePrevious(), facility.getOutstandingAmount()));
//                    }
//                }
//            }
//            return getExposureValues(facilities, parentFacility, calculateExposureRS);
//        }
//    }

    private FacilityPaper calculateFacilityPaperCorporateExposure(FacilityPaper facilityPaper) {
        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposureNewValues = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposureNewValues = new HashMap<>();

        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposurePrevValues = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposurePrevValues = new HashMap<>();

        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposureExistingValues = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposureExistingValues = new HashMap<>();

        CalculateExposureRS calculateExposureRS = new CalculateExposureRS();
        BigDecimal cashAmount = this.facilityPaperJdbcDao.getTotalCashAmount(facilityPaper.getFacilityPaperID());
        calculateExposureRS.setExistingCashMargin(cashAmount);
        calculateExposureRS.setOutstandingCashMargin(cashAmount);
        calculateExposureRS.setProposedCashMargin(cashAmount);

        for (Facility facility : facilityPaper.getActiveFacility()) {

            if (facility.getIsNew() != null && facility.getIsNew().getBoolVal()) {
                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {

                    if (facility.getParentFacilityID() == null) {
                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
                        parentFacilityIDWiseDirectExposureExistingValues.put(facility.getFacilityID(), facility.getExistingAmount());
                    } else if (facility.getParentFacilityID() != null) {
                        parentFacilityIDWiseDirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));

                        parentFacilityIDWiseDirectExposureExistingValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseDirectExposureExistingValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureExistingValues.get(facility.getParentFacilityID()).max(facility.getExistingAmount()));

                    }

                } else {
                    if (facility.getParentFacilityID() == null) {
                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
                        parentFacilityIDWiseIndirectExposureExistingValues.put(facility.getFacilityID(), facility.getExistingAmount());
                    } else if (facility.getParentFacilityID() != null) {
                        parentFacilityIDWiseIndirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));

                        parentFacilityIDWiseIndirectExposureExistingValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseIndirectExposureExistingValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureExistingValues.get(facility.getParentFacilityID()).max(facility.getExistingAmount()));
                    }
                }
                if (facility.getParentFacilityID() == null) {
                    calculateExposureRS.addTotalExposureNew(facility.getFacilityAmount());
                    calculateExposureRS.addTotalExposureExisting(facility.getExistingAmount());
                    calculateExposureRS.addNetTotalExposureNew(facility.getFacilityAmount());
                    calculateExposureRS.addNetTotalExposureExisting(facility.getExistingAmount());
                }
            } else {
                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {

                    if (facility.getParentFacilityID() == null) {
                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
                        parentFacilityIDWiseDirectExposurePrevValues.put(facility.getFacilityID(), facility.getOutstandingAmount());
                        parentFacilityIDWiseDirectExposureExistingValues.put(facility.getFacilityID(), facility.getExistingAmount());
                    } else if (facility.getParentFacilityID() != null) {
                        parentFacilityIDWiseDirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));

                        parentFacilityIDWiseDirectExposurePrevValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseDirectExposurePrevValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposurePrevValues.get(facility.getParentFacilityID()).max(facility.getOutstandingAmount()));

                        parentFacilityIDWiseDirectExposureExistingValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseDirectExposureExistingValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureExistingValues.get(facility.getParentFacilityID()).max(facility.getExistingAmount()));

                    }
                } else {
                    if (facility.getParentFacilityID() == null) {
                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
                        parentFacilityIDWiseIndirectExposurePrevValues.put(facility.getFacilityID(), facility.getOutstandingAmount());
                        parentFacilityIDWiseIndirectExposureExistingValues.put(facility.getFacilityID(), facility.getExistingAmount());
                    } else if (facility.getParentFacilityID() != null) {
                        parentFacilityIDWiseIndirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));

                        parentFacilityIDWiseIndirectExposurePrevValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseIndirectExposurePrevValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposurePrevValues.get(facility.getParentFacilityID()).max(facility.getOutstandingAmount()));

                        parentFacilityIDWiseIndirectExposureExistingValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseIndirectExposureExistingValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureExistingValues.get(facility.getParentFacilityID()).max(facility.getExistingAmount()));
                    }
                }
                if (facility.getParentFacilityID() == null) {
                    calculateExposureRS.addTotalExposureNew(facility.getFacilityAmount());
                    calculateExposureRS.addTotalExposureExisting(facility.getExistingAmount());
                    calculateExposureRS.addTotalExposurePrevious(facility.getOutstandingAmount());

                    calculateExposureRS.addNetTotalExposureNew(facility.getFacilityAmount());
                    calculateExposureRS.addNetTotalExposureExisting(facility.getExistingAmount());
                    calculateExposureRS.addNetTotalExposurePrevious(facility.getOutstandingAmount());
                }
            }
        }

        if (!calculateExposureRS.getTotalExposureNew().equals(DecimalCalculator.getDefaultZero())) {
            calculateExposureRS.setNetTotalExposureNew(DecimalCalculator.subtract(calculateExposureRS.getTotalExposureNew(), cashAmount));
        }

        if (!calculateExposureRS.getTotalExposurePrevious().equals(DecimalCalculator.getDefaultZero())) {
            calculateExposureRS.setNetTotalExposurePrevious(DecimalCalculator.subtract(calculateExposureRS.getTotalExposurePrevious(), cashAmount));
        }

        if (!calculateExposureRS.getNetTotalExposureExisting().equals(DecimalCalculator.getDefaultZero())) {
            calculateExposureRS.setNetTotalExposureExisting(DecimalCalculator.subtract(calculateExposureRS.getNetTotalExposureExisting(), cashAmount));
        }

        for (Integer facilityID : parentFacilityIDWiseDirectExposureNewValues.keySet()) {
            calculateExposureRS.addTotalDirectExposureNew(parentFacilityIDWiseDirectExposureNewValues.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseIndirectExposureNewValues.keySet()) {
            calculateExposureRS.addTotalIndirectExposureNew(parentFacilityIDWiseIndirectExposureNewValues.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseDirectExposurePrevValues.keySet()) {
            calculateExposureRS.addTotalDirectExposurePrevious(parentFacilityIDWiseDirectExposurePrevValues.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseIndirectExposurePrevValues.keySet()) {
            calculateExposureRS.addTotalIndirectExposurePrevious(parentFacilityIDWiseIndirectExposurePrevValues.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseDirectExposureExistingValues.keySet()) {
            calculateExposureRS.addTotalDirectExposureExisting(parentFacilityIDWiseDirectExposureExistingValues.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseIndirectExposureExistingValues.keySet()) {
            calculateExposureRS.addTotalIndirectExposureExisting(parentFacilityIDWiseIndirectExposureExistingValues.get(facilityID));
        }

        facilityPaper.setTotalDirectExposurePrevious(calculateExposureRS.getTotalDirectExposurePrevious());
        facilityPaper.setTotalDirectExposureNew(calculateExposureRS.getTotalDirectExposureNew());
        facilityPaper.setTotalIndirectExposurePrevious(calculateExposureRS.getTotalIndirectExposurePrevious());
        facilityPaper.setTotalIndirectExposureNew(calculateExposureRS.getTotalIndirectExposureNew());
        facilityPaper.setTotalExposurePrevious(calculateExposureRS.getTotalExposurePrevious());
        facilityPaper.setTotalExposureNew(calculateExposureRS.getTotalExposureNew());

        facilityPaper.setTotalIndirectExposureExisting(calculateExposureRS.getTotalIndirectExposureExisting());
        facilityPaper.setTotalDirectExposureExisting(calculateExposureRS.getTotalDirectExposureExisting());
        facilityPaper.setTotalExposureExisting(calculateExposureRS.getTotalExposureExisting());
        facilityPaper.setNetTotalExposureNew(calculateExposureRS.getNetTotalExposureNew());
        facilityPaper.setNetTotalExposurePrevious(calculateExposureRS.getNetTotalExposurePrevious());
        facilityPaper.setNetTotalExposureExisting(calculateExposureRS.getNetTotalExposureExisting());
        facilityPaper.setExistingCashMargin(calculateExposureRS.getExistingCashMargin());
        facilityPaper.setOutstandingCashMargin(calculateExposureRS.getOutstandingCashMargin());
        facilityPaper.setProposedCashMargin(calculateExposureRS.getProposedCashMargin());

        return facilityPaper;

    }

//    private FacilityPaper calculateFacilityPaperCorporateExposure(FacilityPaper facilityPaper) {
//        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposureNewValues = new HashMap<>();
//        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposureNewValues = new HashMap<>();
//
//        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposurePrevValues = new HashMap<>();
//        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposurePrevValues = new HashMap<>();
//
//        Map<Integer, BigDecimal> parentFacilityIDWiseDirectExposureExistingValues = new HashMap<>();
//        Map<Integer, BigDecimal> parentFacilityIDWiseIndirectExposureExistingValues = new HashMap<>();
//
//        CalculateExposureRS calculateExposureRS = new CalculateExposureRS();
//        BigDecimal cashAmount = this.facilityPaperJdbcDao.getTotalCashAmount(facilityPaper.getFacilityPaperID());
//        calculateExposureRS.setExistingCashMargin(cashAmount);
//        calculateExposureRS.setOutstandingCashMargin(cashAmount);
//        calculateExposureRS.setProposedCashMargin(cashAmount);
//
//        for (Facility facility : facilityPaper.getActiveFacility()) {
//
//            if (facility.getIsNew() != null && facility.getIsNew().getBoolVal()) {
//                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//
//                    if (facility.getParentFacilityID() == null) {
//                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
//                        parentFacilityIDWiseDirectExposureExistingValues.put(facility.getFacilityID(), facility.getExistingAmount());
//                    } else if (facility.getParentFacilityID() != null) {
//                        parentFacilityIDWiseDirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
//
//                        parentFacilityIDWiseDirectExposureExistingValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseDirectExposureExistingValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureExistingValues.get(facility.getParentFacilityID()).max(facility.getExistingAmount()));
//
//                    }
//
//                } else {
//                    if (facility.getParentFacilityID() == null) {
//                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
//                        parentFacilityIDWiseIndirectExposureExistingValues.put(facility.getFacilityID(), facility.getExistingAmount());
//                    } else if (facility.getParentFacilityID() != null) {
//                        parentFacilityIDWiseIndirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
//
//                        parentFacilityIDWiseIndirectExposureExistingValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseIndirectExposureExistingValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureExistingValues.get(facility.getParentFacilityID()).max(facility.getExistingAmount()));
//                    }
//                }
//                if (facility.getParentFacilityID() == null && facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//                    calculateExposureRS.addTotalExposureNew(facility.getFacilityAmount());
//                    calculateExposureRS.addTotalExposureExisting(facility.getExistingAmount());
//                    calculateExposureRS.addNetTotalExposureNew(facility.getFacilityAmount());
//                    calculateExposureRS.addNetTotalExposureExisting(facility.getExistingAmount());
//                }
//            } else {
//                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//
//                    if (facility.getParentFacilityID() == null) {
//                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
//                        parentFacilityIDWiseDirectExposurePrevValues.put(facility.getFacilityID(), facility.getOutstandingAmount());
//                        parentFacilityIDWiseDirectExposureExistingValues.put(facility.getFacilityID(), facility.getExistingAmount());
//                    } else if (facility.getParentFacilityID() != null) {
//                        parentFacilityIDWiseDirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseDirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
//
//                        parentFacilityIDWiseDirectExposurePrevValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseDirectExposurePrevValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposurePrevValues.get(facility.getParentFacilityID()).max(facility.getOutstandingAmount()));
//
//                        parentFacilityIDWiseDirectExposureExistingValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseDirectExposureExistingValues.put(facility.getParentFacilityID(), parentFacilityIDWiseDirectExposureExistingValues.get(facility.getParentFacilityID()).max(facility.getExistingAmount()));
//
//                    }
//                } else {
//                    if (facility.getParentFacilityID() == null) {
//                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getFacilityID(), facility.getFacilityAmount());
//                        parentFacilityIDWiseIndirectExposurePrevValues.put(facility.getFacilityID(), facility.getOutstandingAmount());
//                        parentFacilityIDWiseIndirectExposureExistingValues.put(facility.getFacilityID(), facility.getExistingAmount());
//                    } else if (facility.getParentFacilityID() != null) {
//                        parentFacilityIDWiseIndirectExposureNewValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseIndirectExposureNewValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureNewValues.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
//
//                        parentFacilityIDWiseIndirectExposurePrevValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseIndirectExposurePrevValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposurePrevValues.get(facility.getParentFacilityID()).max(facility.getOutstandingAmount()));
//
//                        parentFacilityIDWiseIndirectExposureExistingValues.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
//                        parentFacilityIDWiseIndirectExposureExistingValues.put(facility.getParentFacilityID(), parentFacilityIDWiseIndirectExposureExistingValues.get(facility.getParentFacilityID()).max(facility.getExistingAmount()));
//                    }
//                }
//                if (facility.getParentFacilityID() == null && facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {
//                    calculateExposureRS.addTotalExposureNew(facility.getFacilityAmount());
//                    calculateExposureRS.addTotalExposureExisting(facility.getExistingAmount());
//                    calculateExposureRS.addTotalExposurePrevious(facility.getOutstandingAmount());
//
//                    calculateExposureRS.addNetTotalExposureNew(facility.getFacilityAmount());
//                    calculateExposureRS.addNetTotalExposureExisting(facility.getExistingAmount());
//                    calculateExposureRS.addNetTotalExposurePrevious(facility.getOutstandingAmount());
//                }
//            }
//        }
//
//        calculateExposureRS.setNetTotalExposureNew(DecimalCalculator.subtract(calculateExposureRS.getTotalExposureNew(), cashAmount));
//        calculateExposureRS.setNetTotalExposurePrevious(DecimalCalculator.subtract(calculateExposureRS.getTotalExposurePrevious(), cashAmount));
//        calculateExposureRS.setNetTotalExposureExisting(DecimalCalculator.subtract(calculateExposureRS.getNetTotalExposureExisting(), cashAmount));
//
//        for (Integer facilityID : parentFacilityIDWiseDirectExposureNewValues.keySet()) {
//            calculateExposureRS.addTotalDirectExposureNew(parentFacilityIDWiseDirectExposureNewValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseIndirectExposureNewValues.keySet()) {
//            calculateExposureRS.addTotalIndirectExposureNew(parentFacilityIDWiseIndirectExposureNewValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseDirectExposurePrevValues.keySet()) {
//            calculateExposureRS.addTotalDirectExposurePrevious(parentFacilityIDWiseDirectExposurePrevValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseIndirectExposurePrevValues.keySet()) {
//            calculateExposureRS.addTotalIndirectExposurePrevious(parentFacilityIDWiseIndirectExposurePrevValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseDirectExposureExistingValues.keySet()) {
//            calculateExposureRS.addTotalDirectExposureExisting(parentFacilityIDWiseDirectExposureExistingValues.get(facilityID));
//        }
//
//        for (Integer facilityID : parentFacilityIDWiseIndirectExposureExistingValues.keySet()) {
//            calculateExposureRS.addTotalIndirectExposureExisting(parentFacilityIDWiseIndirectExposureExistingValues.get(facilityID));
//        }
//
//        facilityPaper.setTotalDirectExposurePrevious(calculateExposureRS.getTotalDirectExposurePrevious());
//        facilityPaper.setTotalDirectExposureNew(calculateExposureRS.getTotalDirectExposureNew());
//        facilityPaper.setTotalIndirectExposurePrevious(calculateExposureRS.getTotalIndirectExposurePrevious());
//        facilityPaper.setTotalIndirectExposureNew(calculateExposureRS.getTotalIndirectExposureNew());
//        facilityPaper.setTotalExposurePrevious(calculateExposureRS.getTotalExposurePrevious());
//        facilityPaper.setTotalExposureNew(calculateExposureRS.getTotalExposureNew());
//
//        facilityPaper.setTotalIndirectExposureExisting(calculateExposureRS.getTotalIndirectExposureExisting());
//        facilityPaper.setTotalDirectExposureExisting(calculateExposureRS.getTotalDirectExposureExisting());
//        facilityPaper.setTotalExposureExisting(calculateExposureRS.getTotalExposureExisting());
//        facilityPaper.setNetTotalExposureNew(calculateExposureRS.getNetTotalExposureNew());
//        facilityPaper.setNetTotalExposurePrevious(calculateExposureRS.getNetTotalExposurePrevious());
//        facilityPaper.setNetTotalExposureExisting(calculateExposureRS.getNetTotalExposureExisting());
//        facilityPaper.setExistingCashMargin(calculateExposureRS.getExistingCashMargin());
//        facilityPaper.setOutstandingCashMargin(calculateExposureRS.getOutstandingCashMargin());
//        facilityPaper.setProposedCashMargin(calculateExposureRS.getProposedCashMargin());
//
//        return facilityPaper;
//
//    }
}
