package com.itechro.cas.model.dto.bcc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class FPBccDocStorageDTO implements Serializable {

    private Integer docStorageID;

    private String description;

    private String fileName;

    private byte[] document;

    private Date lastUpdatedDateStr;
}
