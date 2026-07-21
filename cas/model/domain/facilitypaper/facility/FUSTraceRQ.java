package com.itechro.cas.model.domain.facilitypaper.facility;

import lombok.Data;

import java.util.Date;

@Data
public class FUSTraceRQ {

   private  Integer id;

   private Integer mainKey;

   private Integer subKey;

   private Integer parentRecId;

   private String createdBy;

   private Date createdDate;

   private String comment;

   private String flag;

   private String status;

   private String createdUserDisplayName;

   private Date modifiedDate;

   private String modifiedBy;

   private Integer createdUserWC;

   private String createdUserDesignation;

}
