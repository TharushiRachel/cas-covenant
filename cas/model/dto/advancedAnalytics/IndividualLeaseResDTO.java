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
public class IndividualLeaseResDTO {

  @JsonProperty("statusCode")
  private Object statusCode;

  @JsonProperty("statusDescription")
  private Object statusDescription;

  @JsonProperty("timestamp")
  private Object timestamp;

  @JsonProperty("payload")
  private IndividualLeaseResPayload payload;

  @JsonProperty("errors")
  private Object errors;
}
