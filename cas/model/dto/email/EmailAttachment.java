package com.itechro.cas.model.dto.email;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailAttachment implements Serializable {

    private String fileName;

    private String fileDataUri;
}
