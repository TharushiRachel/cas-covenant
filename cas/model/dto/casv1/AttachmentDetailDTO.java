package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.AttachmentDetail;
import lombok.Data;

import java.util.Date;

@Data
public class AttachmentDetailDTO {

    private Integer sectionID;
    private String fileName;
    private byte[] fileContent;
    private String lastModifiedDate;
    private String codeDesc;
}
