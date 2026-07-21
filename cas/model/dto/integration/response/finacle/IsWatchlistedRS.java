package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

import java.io.Serializable;
@Data
public class IsWatchlistedRS implements Serializable{
        private String Status;
        private String refId;
        private String isCustomerWatchListed;


}
