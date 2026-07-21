/*
 * -------------------------------------------------------------------------------------------------------------------
 * Copyright © Sampath Bank PLC. All rights reserved.
 *
 * <p>This software and its source code are the exclusive property of Sampath Bank PLC. Unauthorized
 * copying, modification, distribution, or use - whether in whole or in part - is strictly
 * prohibited without prior written consent from Sampath Bank PLC.
 * -------------------------------------------------------------------------------------------------------------------
 */
package com.itechro.cas.model.dto.advancedAnalytics;

import lombok.Data;

@Data
public class IndividualLeaseRequestDTO {

  private Integer leadId;

  private String leadRef;

  private Object oldNicCrib;

  private Object newNicCrib;

  private Object cooperateCrib;

  private Object customerType;
}
