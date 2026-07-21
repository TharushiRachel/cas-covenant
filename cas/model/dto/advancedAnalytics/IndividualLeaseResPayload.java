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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IndividualLeaseResPayload {
  @JsonProperty("customer_id")
  private Object customer_id;

  @JsonProperty("risk_level_bureau")
  private Object risk_level_bureau;

  @JsonProperty("risk_level_casa")
  private Object risk_level_casa;

  @JsonProperty("pentile")
  private Object pentile;

  @JsonProperty("Final_Obligation")
  private Object Final_Obligation;

  @JsonProperty("loan_interest")
  private Object loan_interest;

  @JsonProperty("decision")
  private Object decision;

  @JsonProperty("channel")
  private Object channel;

  @JsonProperty("rejected_reason")
  private Object rejected_reason;
}
